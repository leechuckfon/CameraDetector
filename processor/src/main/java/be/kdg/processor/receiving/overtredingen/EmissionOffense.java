package be.kdg.processor.receiving.overtredingen;

import be.kdg.processor.receiving.adapters.CameraAdapter;
import be.kdg.processor.receiving.adapters.LicensePlateAdapter;
import be.kdg.processor.model.CameraMessage;
import be.kdg.processor.model.fine.calculcators.EmissionCalculator;
import be.kdg.processor.model.camera.Camera;
import be.kdg.processor.model.licenseplate.LicensePlateInfo;
import be.kdg.processor.web.services.FineService;
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

/**
 * The EmissionOffense class will check with every message that arrived if the camera that the car has passed has a higher Euronorm number than the car itself. If this is the case
 * then the class will ask the EmissionCalculator to make a Fine object.
 *
 * If the CameraMessage gives back an exception then this will be further thrown to the FineAnalyser object.
 * (Will also cache the CameraMessage Objects which couldn't get processed if this line is not removed yet).
 */


@Component
public class EmissionOffense implements Offense {

    @Autowired
    private CameraAdapter ca;
    @Autowired
    private LicensePlateAdapter lps;
    private static final Logger LOGGER = LoggerFactory.getLogger(EmissionOffense.class);
    private ConcurrentHashMap<String, LocalDateTime> criminals;
    private long emissionTime;
    private final EmissionCalculator emissionCalculator;
    private int finefactor;
    private ConcurrentHashMap<CameraMessage,Integer> cachedMessages;
    private List<Camera> askedCameras;

    public EmissionOffense(@Value("${emissionTime}") long emissionTime, FineService fineService, @Value("${finefactor.emissionfinefactor}") int finefactor, EmissionCalculator emissionCalculator) {
        this.criminals = new ConcurrentHashMap<>();
        this.emissionTime = emissionTime;
        this.finefactor = finefactor;
        this.emissionCalculator = emissionCalculator;
        cachedMessages = new ConcurrentHashMap<>();
        askedCameras = new ArrayList<>();
        ca = new CameraAdapter();
        lps = new LicensePlateAdapter();
    }


    @Override
    public void handleMessage(CameraMessage m) throws IOException {
        try {

            Camera emission = ca.AskInfo(m.getId());
            LicensePlateInfo perp = lps.askInfo(m.getLicensePlate());

            if (perp.getEuroNumber() < emission.getEuroNorm()) {
                if (!criminals.containsKey(perp.getPlateId())) {
                    criminals.put(perp.getPlateId(), m.getTimestamp());

                    emissionCalculator.calculateFine(finefactor,emission.getCameraId(),perp.getEuroNumber(),emission.getEuroNorm(),m.getTimestamp());

                    LOGGER.info("auto: " + perp.getPlateId() + " has an emissionOffense.");
                } else {
                    LocalDateTime laatsteKeer = criminals.get(perp.getPlateId());
                    if (laatsteKeer.until(m.getTimestamp(), ChronoUnit.SECONDS) > emissionTime) {
                        criminals.replace(perp.getPlateId(), m.getTimestamp());
                        LOGGER.info("auto: " + perp.getPlateId() + " has another emissionOffense.");
                    }
                }
            }

        } catch (IOException | CameraNotFoundException | LicensePlateNotFoundException e) {
            if (!cachedMessages.containsKey(m)) {
                cacheMessage(m);
            } else {
                cachedMessages.replace(m, cachedMessages.get(m) + 1);
                System.out.println(cachedMessages);
                if (cachedMessages.get(m) >= 3) {
                    cachedMessages.remove(m);
                }
            }
            throw(e);
        }
    }
    @Scheduled(fixedDelay = 60000L)
    private void deleteDetections(){
        for (String s : criminals.keySet()) {
            LocalDateTime ldt = criminals.get(s);
            Long timeSinceOffense = ldt.until(LocalDateTime.now(),ChronoUnit.SECONDS);
            if (timeSinceOffense > emissionTime) {
                criminals.remove(s);
            }
        }
    }
    @Scheduled(fixedDelay = 60000L)
    private void retryMessage() {
        for (CameraMessage cameraMessage : cachedMessages.keySet()) {
            if (cameraMessage != null) {
                try {
                    handleMessage(cameraMessage);
                } catch (IOException e) {
                    LOGGER.error("Error has occurred while retrying the message");
                }
            }
        }
    }

    private void cacheMessage(CameraMessage m) {
        cachedMessages.put(m,0);
    }
}
