package be.kdg.processor.overtredingen;

import be.kdg.processor.adapters.CameraAdapter;
import be.kdg.processor.adapters.LicensePlateAdapter;
import be.kdg.processor.model.CameraMessage;
import be.kdg.processor.model.boete.Calculators.EmissieBerekening;
import be.kdg.processor.model.camera.Camera;
import be.kdg.processor.model.licenseplate.LicensePlateInfo;
import be.kdg.processor.services.BoeteService;
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
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class EmissieOvertreding implements Overtreding {

    @Autowired
    private CameraAdapter ca;
    @Autowired
    private LicensePlateAdapter lps;
    private static final Logger LOGGER = LoggerFactory.getLogger(EmissieOvertreding.class);
    private ConcurrentHashMap<String, LocalDateTime> criminelen;
    private long emissieTijd;
    private final EmissieBerekening emissieBerekening;
    private int boetefactor;
    private ConcurrentHashMap<CameraMessage,Integer> cachedMessages;
    private List<Camera> opgevraagdeCameras;

    public EmissieOvertreding( @Value("${emissieTijd}") long emissieTijd, BoeteService boeteService, @Value("${boetefactoren.emissieboetefactor}") int boetefactor, EmissieBerekening emissieBerekening) {
        this.criminelen = new ConcurrentHashMap<>();
        this.emissieTijd = emissieTijd;
        this.boetefactor = boetefactor;
        this.emissieBerekening = emissieBerekening;
        cachedMessages = new ConcurrentHashMap<>();
        opgevraagdeCameras = new ArrayList<>();
    }


    @Override
    public void handleMessage(CameraMessage m) {
        try {
            List<Integer> ids = opgevraagdeCameras.stream().map(x -> x.getCameraId()).collect(Collectors.toList());
            if (!ids.contains(m.getId())) {
                ca = new CameraAdapter();
                lps = new LicensePlateAdapter();
                Camera emissie = ca.AskInfo(m.getId());
                opgevraagdeCameras.add(emissie);
            }

            Camera emissie = opgevraagdeCameras.stream().filter(x -> x.getCameraId() == m.getId()).findFirst().get();
            System.out.println(ids);
            LicensePlateInfo perp = lps.askInfo(m.getLicensePlate());

            if (perp.getEuroNumber() < emissie.getEuroNorm()) {
                if (!criminelen.containsKey(perp.getPlateId())) {
                    criminelen.put(perp.getPlateId(), m.getTimestamp());

                    emissieBerekening.berekenBoete(boetefactor,emissie.getCameraId(),perp.getEuroNumber(),emissie.getEuroNorm(),m.getTimestamp());

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
                //cache bestand voor latere check
                
            }
            if (e.getClass() == CameraNotFoundException.class) {
                LOGGER.warn("Camera niet gevonden");

            }
            if (e.getClass() == LicensePlateNotFoundException.class) {
                LOGGER.warn("Nummerplaat niet gevonden");
            }
            if (!cachedMessages.containsKey(m)) {
                cacheMessage(m);
            } else {
                cachedMessages.replace(m, cachedMessages.get(m) + 1);
                System.out.println(cachedMessages);
                if (cachedMessages.get(m) >= 3) {
                    cachedMessages.remove(m);
                }
            }
        }
    }
    @Scheduled(fixedDelay = 60000L)
    private void verwijderDetecties(){
        for (String s : criminelen.keySet()) {
            LocalDateTime ldt = criminelen.get(s);
            Long tijdSindsBoete = ldt.until(LocalDateTime.now(),ChronoUnit.SECONDS);
            if (tijdSindsBoete > emissieTijd) {
                criminelen.remove(s);
            }
        }
    }
    @Scheduled(fixedDelay = 60000L)
    private void retryMessage() {
        for (CameraMessage cameraMessage : cachedMessages.keySet()) {
            if (cameraMessage != null) {
                handleMessage(cameraMessage);
            }
        }
    }

    private void cacheMessage(CameraMessage m) {
        cachedMessages.put(m,0);
    }
}
