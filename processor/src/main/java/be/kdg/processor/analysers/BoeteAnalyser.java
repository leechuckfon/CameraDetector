package be.kdg.processor.analysers;

import be.kdg.processor.model.CameraMessage;
import be.kdg.sa.services.CameraNotFoundException;
import be.kdg.sa.services.LicensePlateNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class BoeteAnalyser {
    private static final Logger LOGGER = LoggerFactory.getLogger(BoeteAnalyser.class);

    public void checkOvertreding(Overtreding o, CameraMessage m) {

        try {
            o.checkOvertreding(m);
        } catch (CameraNotFoundException | LicensePlateNotFoundException e) {
//            if (e.getClass() == IOException.class) {
//                LOGGER.warn("IOException gebeurd tijdens het checken van overtredingen");
//            }
            if (e.getClass() == CameraNotFoundException.class) {
                LOGGER.warn("Camera niet gevonden");
            }
            if (e.getClass() == LicensePlateNotFoundException.class) {
                LOGGER.warn("Nummerplaat niet gevonden");
            }
        }
    }
}
