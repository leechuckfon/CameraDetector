package be.kdg.processor.analysers;

import be.kdg.processor.adapters.CameraAdapter;
import be.kdg.processor.adapters.LicensePlateAdapter;
import be.kdg.processor.model.CameraMessage;
import be.kdg.processor.model.camera.Camera;
import be.kdg.processor.model.licenseplate.LicensePlateInfo;
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
import java.util.HashMap;
import java.util.Map;
@Component
public class EmissieOvertreding implements Overtreding {

    @Autowired
    private CameraAdapter ca;
    @Autowired
    private LicensePlateAdapter lps;
    private static final Logger LOGGER = LoggerFactory.getLogger(EmissieOvertreding.class);
    private Map<String, LocalDateTime> criminelen;
    private long emissieTijd;

    public EmissieOvertreding( @Value("${emissieTijd}") long emissieTijd) {
        this.criminelen = new HashMap<>();
        this.emissieTijd = emissieTijd;
    }


    @Override
    public void handleMessage(CameraMessage m) {
        try {
            ca = new CameraAdapter();
            lps = new LicensePlateAdapter();
            Camera emissie = ca.AskInfo(m.getId());
            LicensePlateInfo perp = lps.askInfo(m.getLicensePlate());

            if (perp.getEuroNumber() < emissie.getEuroNorm()) {
                if (!criminelen.containsKey(perp.getPlateId())) {
                    criminelen.put(perp.getPlateId(), m.getTimestamp());
                    LOGGER.info("auto: " + perp.getPlateId() + " heeft een emissieovertreding.");
                } else {
                    LocalDateTime laatsteKeer = criminelen.get(perp.getPlateId());
                    if (laatsteKeer.until(m.getTimestamp(), ChronoUnit.SECONDS) > emissieTijd) {
                        criminelen.replace(perp.getPlateId(), m.getTimestamp());
                        LOGGER.info("auto: " + perp.getPlateId() + " heeft nog een emissieovertreding.");
                    }
                }
            }


            for (String s : criminelen.keySet()) {
                LocalDateTime ldt = criminelen.get(s);
                ldt.until(LocalDateTime.now(),ChronoUnit.SECONDS);

            }


        } catch (IOException | CameraNotFoundException | LicensePlateNotFoundException e) {
            if (e.getClass() == IOException.class) {
                LOGGER.warn("IOException gebeurd tijdens het checken van overtredingen");
                //cache bestand voor latere check
                
            }
            if (e.getClass() == CameraNotFoundException.class) {
                LOGGER.warn("Camera niet gevonden");

            }
            if (e.getClass() == LicensePlateNotFoundException.class) {
                LOGGER.warn("Nummerplaat niet gevonden");
            }
        }
    }

    private void cacheMessage(CameraMessage m) {

    }
}
