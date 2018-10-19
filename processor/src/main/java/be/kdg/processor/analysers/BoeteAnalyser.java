package be.kdg.processor.analysers;

import be.kdg.processor.model.CameraMessage;
import be.kdg.processor.overtredingen.EmissieOvertreding;
import be.kdg.processor.overtredingen.SnelheidsOvertreding;
import be.kdg.sa.services.CameraNotFoundException;
import be.kdg.sa.services.LicensePlateNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * De BoeteAnalyser zal elke message dat er wordt doorgegeven van de MessageListener doorgeven aan alle OvertredingsObjecten om te kijken of er overtredingen zijn gebeurd.
 */

@Component
public class BoeteAnalyser {
    private static final Logger LOGGER = LoggerFactory.getLogger(BoeteAnalyser.class);
    @Autowired
    private EmissieOvertreding eo;
    @Autowired
    private SnelheidsOvertreding so;


    public void checkOvertreding(CameraMessage m) {
        try {
        eo.handleMessage(m);
        so.handleMessage(m);

        } catch (CameraNotFoundException | LicensePlateNotFoundException e) {
            if (e.getClass() == CameraNotFoundException.class) {
                LOGGER.warn("Camera niet gevonden");
            }
            if (e.getClass() == LicensePlateNotFoundException.class) {
                LOGGER.warn("Nummerplaat niet gevonden");
            }
        }
    }
}
