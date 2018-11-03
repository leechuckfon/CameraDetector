package be.kdg.simulator.config;

import be.kdg.simulator.generators.MessageGenerator;
import be.kdg.simulator.model.CameraMessage;
import be.kdg.simulator.posting.messengers.Messenger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpIOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * The MessageScheduler is the class that will make sure for the periodic posting of messages on the RabbitMQ
 */
@Component
public class MessageScheduler {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageScheduler.class);

    private ScheduledExecutorService scheduler;
    private MessageGenerator gen;
    private Messenger mess;
    private Long delay;
    private String rushHours;
    private ArrayList<LocalTime> beginEndRushHours;
    private String type;

    public MessageScheduler(MessageGenerator gen, Messenger mess, @Value("${frequency}") Long delay, @Value("${rHours}") String rushHours, @Value("${generator.type}") String type) {
        this.gen = gen;
        this.mess = mess;
        this.delay = delay;
        this.rushHours = rushHours;
        this.type = type;
        beginEndRushHours = new ArrayList<>();
    }

    //Deze methode wordt aangeroepen wanneer Spring volledig geïnitialiseerd is
    @EventListener(ApplicationReadyEvent.class)
    public void startup() {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        tick();
    }

    private void tick() {
        CameraMessage cam = gen.generate();

        LOGGER.info("Generated Message: " + cam);
        if (cam != null) {
            if (cam.getDelay() != -1) {
                delay = cam.getDelay();

                if (type.equals("random")) {
                    if (beginEndRushHours.isEmpty()) {
                        String[] allRushes = rushHours.split(",");
                        for (String s : allRushes) {
                            String[] beginEnd = s.split("-");
                            String[] begin = beginEnd[0].split(":");
                            String[] end = beginEnd[1].split(":");
                            beginEndRushHours.add(LocalTime.of(Integer.parseInt(begin[0]),Integer.parseInt(begin[1])));
                            beginEndRushHours.add(LocalTime.of(Integer.parseInt(end[0]),Integer.parseInt(end[1])));
                        }
                    }
                    for (int i=0;i<beginEndRushHours.size();i+=2) {
                        if (LocalTime.now().isAfter(beginEndRushHours.get(i)) && LocalTime.now().isBefore(beginEndRushHours.get(i+1))) {
                            delay = 500L;
                        }
                    }
                }
                System.out.println(delay);
                scheduler.schedule(this::tick, delay, TimeUnit.MILLISECONDS);
                try {
                    mess.sendMessage(cam);
                }catch (AmqpIOException amqpException) {
                    LOGGER.error(amqpException.getMessage());
                }
                LOGGER.info(cam + "is verzonden");
            }
        } else {
            System.exit(0);
        }
    }
}