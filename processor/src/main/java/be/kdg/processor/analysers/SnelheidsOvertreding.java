package be.kdg.processor.analysers;

import be.kdg.processor.adapters.CameraAdapter;
import be.kdg.processor.adapters.LicensePlateAdapter;
import be.kdg.processor.model.CameraMessage;
import be.kdg.sa.services.CameraNotFoundException;
import be.kdg.sa.services.LicensePlateNotFoundException;
import ch.qos.logback.core.util.FixedDelay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class SnelheidsOvertreding {

    private final CameraAdapter ca;
    private final LicensePlateAdapter lps;
    private static final Logger LOGGER = LoggerFactory.getLogger(SnelheidsOvertreding.class);
    private List<CameraMessage> bufferedMessages = new ArrayList<>();
    private Map<String,List<CameraMessage>> beginEind;
    private final long delay;


    @Autowired
    public SnelheidsOvertreding(@Value("${timeframeSnelheid}") long delay, CameraAdapter ca, LicensePlateAdapter lps) {
        this.delay = delay;
        this.ca = ca;
        this.lps = lps;
    }

    //buffer de messages
    public void handleMessage(CameraMessage m) {
        if (m != null) {
            bufferedMessages.add(m);
        }
        checkOvertreding();
    }

    @Scheduled(fixedDelay = 60000L)
    public void verwijderBufferedMessages() {
        List<CameraMessage> matchedMessages =  bufferedMessages.stream().filter((x) -> x.getTimestamp().until(LocalDateTime.now(),ChronoUnit.MILLIS) > delay).collect(Collectors.toList());
        for (CameraMessage matchedMessage : matchedMessages) {
            bufferedMessages.remove(matchedMessage);
        }
    }

    //maak methode die via @Scheduled om de zoveel tijd de messages in de buffer checkt.
//    @Scheduled(fixedDelayString = "${timeframeSnelheid}" )
    public void checkOvertreding() {

            try {
                beginEind = new HashMap<>();
                for (CameraMessage bufferedMessage : bufferedMessages) {
                    if(!(beginEind.containsKey(bufferedMessage.getLicensePlate()))){
                        beginEind.put(bufferedMessage.getLicensePlate(),new ArrayList<>());
                    }
                        beginEind.get(bufferedMessage.getLicensePlate()).add(bufferedMessage);

                }

//                ca = new CameraAdapter();
//                lps = new LicensePlateAdapter();

                for (String s : beginEind.keySet()) {
                    if (beginEind.get(s).size() > 1) {
                        CameraMessage begin = beginEind.get(s).get(0);
                        CameraMessage eind = beginEind.get(s).get(1);
                        int max_snelheid = ca.AskInfo(begin.getId()).getSegment().getSpeedLimit();
                        LocalDateTime b = begin.getTimestamp();
                        LocalDateTime e = eind.getTimestamp();
                        int afstand = ca.AskInfo(begin.getId()).getSegment().getDistance();
                        float millis = (b.until(e, ChronoUnit.MILLIS));
                        float speed = ((float) afstand/1000) / (millis / 360000);
                        if (speed > max_snelheid) {
                            /* maak boete */
                            bufferedMessages.removeAll(bufferedMessages.stream().filter(x -> x.getLicensePlate().equals(s)).collect(Collectors.toList()));
                            System.out.println(speed);
                            System.out.println(s + " heeft een boete voor te snel rijden.");
                        }
                    }
                }




            } catch (IOException | CameraNotFoundException | LicensePlateNotFoundException e) {
                if (e.getClass() == IOException.class) {
                    LOGGER.warn("IOException gebeurd tijdens het checken van overtredingen");
                }
                if (e.getClass() == CameraNotFoundException.class) {
                    LOGGER.warn("Camera niet gevonden");
                }
                if (e.getClass() == LicensePlateNotFoundException.class) {
                    LOGGER.warn("Nummerplaat niet gevonden");
                }

            }
    }
}

