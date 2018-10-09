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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SnelheidsOvertreding implements Overtreding {

    @Autowired
    private CameraAdapter ca;
    @Autowired
    private LicensePlateAdapter lps;
    private long start;
    private long end;
    private static final Logger LOGGER = LoggerFactory.getLogger(SnelheidsOvertreding.class);
    private static List<CameraMessage> bufferedMessages = new ArrayList<>();

    public SnelheidsOvertreding(long start, long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public void checkOvertreding(CameraMessage m) {
        if (start < end) {
            if (m != null) {
                bufferedMessages.add(m);
            }
            for (CameraMessage bufferedMessage : bufferedMessages) {
                System.out.println(bufferedMessage);
                System.out.println(bufferedMessages.size());
            }
        } else {
            try {
                ca = new CameraAdapter();
                lps = new LicensePlateAdapter();
                Camera snelheid = ca.AskInfo(m.getId());
                LicensePlateInfo lpi = lps.askInfo(m.getLicensePlate());


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
}
