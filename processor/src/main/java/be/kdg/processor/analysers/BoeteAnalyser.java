package be.kdg.processor.analysers;

import be.kdg.processor.adapters.CameraAdapter;
import be.kdg.processor.adapters.LicensePlateAdapter;
import be.kdg.processor.model.camera.Camera;
import be.kdg.processor.model.CameraMessage;
import be.kdg.processor.model.licenseplate.LicensePlateInfo;
import be.kdg.sa.services.CameraNotFoundException;
import be.kdg.sa.services.LicensePlateNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Component
public class BoeteAnalyser {
    @Autowired
    private CameraAdapter ca;
    @Autowired
    private LicensePlateAdapter lps;
    private static final Logger LOGGER = LoggerFactory.getLogger(BoeteAnalyser.class);
    private Map<String, LocalDateTime> criminelen;
    private long emissieTijd;

    public BoeteAnalyser( @Value("${emissieTijd}") long emissieTijd) {
        this.criminelen = new HashMap<>();
        this.emissieTijd = emissieTijd;
    }

    public void checkEmissieOvertreding(CameraMessage m) {
        try {
            Camera emissie = ca.AskInfo(m.getId());
            LicensePlateInfo perp = lps.askInfo(m.getLicensePlate());

//            System.out.println("Camera: " +emissie.getCameraId());
//            System.out.println("euroNorm:" + emissie.getEuroNorm());
//            System.out.println("nummer:" + perp.getEuroNumber());

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

    public void checkSnelheidOvertreding(CameraMessage m) {
        //TODO: snelheidOvertreding toevoegen
//        Camera emissie = ca.AskInfo(m.getId());
//        LicensePlateInfo perp = lps.askInfo(m.getLicensePlate());
//
//        int help = emissie.getSegment().getDistance();
    }

}
