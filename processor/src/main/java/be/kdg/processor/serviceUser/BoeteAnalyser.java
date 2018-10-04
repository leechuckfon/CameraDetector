package be.kdg.processor.serviceUser;

import be.kdg.processor.model.Camera;
import be.kdg.processor.model.CameraMessage;
import be.kdg.processor.model.LicensePlateInfo;
import be.kdg.sa.services.CameraNotFoundException;
import be.kdg.sa.services.LicensePlateNotFoundException;
import be.kdg.sa.services.LicensePlateServiceProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

@Component
public class BoeteAnalyser {
    @Autowired
    private CameraAnalyser ca;
    @Autowired
    private LicensePlateAnalyser lps;
    private static final Logger LOGGER = LoggerFactory.getLogger(BoeteAnalyser.class);
    private Map<Map<Integer,String>,Map<Integer, LocalDateTime>> aantalKeer = new HashMap<>();

    public void checkOvertredingen(CameraMessage m) {
        try {
            Camera emissie = ca.AskInfo(m.getId());
            System.out.println("Camera: " +emissie.getCameraId());
            System.out.println("euroNorm:" + emissie.getEuroNorm());
            LicensePlateInfo perp = lps.askInfo(m.getLicensePlate());
            System.out.println("nummer:" + perp.getEuroNumber());

            // EMISSIE CONTROLE
            // =======================================================
            //Is hij al eens gepaseerd?
            Map<Integer,String> pairing = new HashMap<>();
            pairing.put(emissie.getCameraId(),perp.getPlateId());
            if (aantalKeer.containsKey(pairing)) {

                //Timestamps vergelijken
                System.out.println("is al eens gepaseserd");
                LocalDateTime laatsteKeer = aantalKeer.get(pairing).get(1);

                //als verschil tussen timestamps < ingestelde tijd
                if (laatsteKeer.until(m.getTimestamp(),ChronoUnit.SECONDS) < 3000) {

                    aantalKeer.get(pairing).put(aantalKeer.get(pairing).size()+1,m.getTimestamp());
                    System.out.println("aantal keren gepaseerd:" + aantalKeer.get(pairing).size());

                    //als plate X meer dan euronorm Y keer is langsgeweest
                    if (aantalKeer.get(pairing).size() == emissie.getEuroNorm()+1) {
                        //TODO: Boete berekening
                        System.out.println("BOETE BEREKENING");
                    }
                }
            } else {
                //als het eerste keer is.
                Map<Integer,LocalDateTime> eersteKeer = new HashMap<>();
                eersteKeer.put(1,m.getTimestamp());
                aantalKeer.put(pairing,eersteKeer);
                System.out.println("eerste keer");
            }
            // ========================================================
            //boete

        } catch (IOException | CameraNotFoundException | LicensePlateNotFoundException e) {
            if (e.getClass() == IOException.class) {
                LOGGER.error("IOException gebeurd tijdens het checken van overtredingen");
            }
            if (e.getClass() == CameraNotFoundException.class) {
                LOGGER.error("Camera niet gevonden");
            }
            if (e.getClass() == LicensePlateNotFoundException.class) {
                LOGGER.error("Nummerplaat niet gevonden");
            }
        }
    }

}
