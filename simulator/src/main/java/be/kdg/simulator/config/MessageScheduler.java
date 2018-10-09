package be.kdg.simulator.config;

import be.kdg.simulator.generators.MessageGenerator;
import be.kdg.simulator.messengers.Messenger;
import be.kdg.simulator.model.CameraMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
@Component
public class MessageScheduler {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageScheduler.class);

    private ScheduledExecutorService scheduler;
    private MessageGenerator gen;
    private Messenger mess;
    private Long delay;

    public MessageScheduler(MessageGenerator gen, Messenger mess, @Value("${frequentie}") Long delay) {
        this.gen = gen;
        this.mess = mess;
        this.delay = delay;
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
            }
        } else {
            return;
        }

        mess.sendMessage(cam);
        LOGGER.info(cam + "is verzonden");
        scheduler.schedule(this::tick, delay, TimeUnit.MILLISECONDS); }
}