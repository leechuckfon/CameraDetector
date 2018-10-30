package be.kdg.processor.receiving.analysers;

import be.kdg.processor.model.CameraMessage;
import be.kdg.processor.receiving.overtredingen.EmissionOffenseChecker;
import be.kdg.processor.receiving.overtredingen.SpeedOffenseChecker;
import be.kdg.sa.services.CameraNotFoundException;
import be.kdg.sa.services.LicensePlateNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * The FineAnalyser will delegate every received CameraMessage to every OffenseChecker Object to see if an offense has occurred.
 * When making a new type of Fine a new OffenseChecker has to be made, autowired and called from this class.
 */

@Component
public class FineAnalyser {
    private static final Logger LOGGER = LoggerFactory.getLogger(FineAnalyser.class);
    private final EmissionOffenseChecker eo;
    private final SpeedOffenseChecker so;

    @Autowired
    public FineAnalyser(EmissionOffenseChecker eo, SpeedOffenseChecker so) {
        this.eo = eo;
        this.so = so;
    }


    public void checkOffenses(CameraMessage m) {
        try {

        eo.handleMessage(m);
        so.handleMessage(m);

        } catch (Exception e) {
            if (e.getClass() == Exception.class) {
                LOGGER.warn("IOException has occurred during the checking of offenses");
            }
            if (e.getClass() == CameraNotFoundException.class) {
                LOGGER.warn("Camera has not been found");
            }
            if (e.getClass() == LicensePlateNotFoundException.class) {
                LOGGER.warn("Nummerplaat has not been found");
            }

        }
    }
}
