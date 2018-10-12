package be.kdg.processor.analysers;

import be.kdg.processor.adapters.CameraAdapter;
import be.kdg.processor.adapters.LicensePlateAdapter;
import be.kdg.processor.model.CameraMessage;
import be.kdg.processor.model.camera.Camera;
import be.kdg.processor.model.licenseplate.LicensePlateInfo;
import be.kdg.sa.services.CameraNotFoundException;
import be.kdg.sa.services.LicensePlateNotFoundException;
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
@Component
public class SnelheidsOvertreding {

    @Autowired
    private CameraAdapter ca;
    @Autowired
    private LicensePlateAdapter lps;
    private static final Logger LOGGER = LoggerFactory.getLogger(SnelheidsOvertreding.class);
    private static List<CameraMessage> bufferedMessages = new ArrayList<>();
    private Map<String,List<CameraMessage>> beginEind = new HashMap<>();
    private final long delay;


    public SnelheidsOvertreding(@Value("${timeframeSnelheid}") long delay) {
        this.delay = delay;
    }

    //buffer de messages
    public void handleMessage(CameraMessage m) {
        if (m != null) {
            bufferedMessages.add(m);
        }

    }

    //maak methode die via @Scheduled om de zoveel tijd de messages in de buffer checkt.
    @Scheduled(fixedDelayString = "${timeframeSnelheid}" )
    public void checkOvertreding() {

            try {

                for (CameraMessage bufferedMessage : bufferedMessages) {
                    if(!(beginEind.containsKey(bufferedMessage.getLicensePlate()))){
                        beginEind.put(bufferedMessage.getLicensePlate(),new ArrayList<>());
                    }
                        beginEind.get(bufferedMessage.getLicensePlate()).add(bufferedMessage);

                }

                ca = new CameraAdapter();
                lps = new LicensePlateAdapter();
                System.out.println("tijd is verstreken");
                for (String s : beginEind.keySet()) {
                    if (beginEind.get(s).size() > 1) {
                        CameraMessage begin = beginEind.get(s).get(0);
                        CameraMessage eind = beginEind.get(s).get(1);
                        int max_snelheid = ca.AskInfo(begin.getId()).getSegment().getSpeedLimit();
                        LocalDateTime b = begin.getTimestamp();
                        LocalDateTime e = eind.getTimestamp();
                        int afstand = ca.AskInfo(begin.getId()).getSegment().getDistance();
                        float millis = (b.until(e, ChronoUnit.MILLIS) / 3600000);
                        if (((long) afstand / millis) > max_snelheid) {
                            System.out.println(s + " heeft een boete voor te snel rijden.");
                        }
                    }
                }

//                int limiet = snelheid.getSegment().getSpeedLimit();
//                int distance = snelheid.getSegment().getDistance();
//                float


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

