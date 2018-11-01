package be.kdg.processor.receiving.overtredingen;

import be.kdg.processor.model.CameraMessage;
import be.kdg.processor.model.camera.Camera;
import be.kdg.processor.model.events.PropertiesChangeEvent;
import be.kdg.processor.model.fine.calculcators.EmissionCalculator;
import be.kdg.processor.model.licenseplate.LicensePlateInfo;
import be.kdg.processor.receiving.adapters.CameraAdapter;
import be.kdg.processor.receiving.adapters.LicensePlateAdapter;
import be.kdg.processor.receiving.configs.PropertiesConfig;
import be.kdg.processor.web.services.FineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

/**
 * The EmissionOffense class will check with every message that arrived if the camera that the car has passed has a higher Euronorm number than the car itself. If this is the case
 * then the class will ask the EmissionCalculator to make a Fine object.
 * <p>
 * If the CameraMessage gives back an exception then this will be further thrown to the FineAnalyser object.
 */


@Component
public class EmissionOffenseChecker implements OffenseChecker, ApplicationListener<PropertiesChangeEvent> {

    private final CameraAdapter ca;
    private final LicensePlateAdapter lps;
    private static final Logger LOGGER = LoggerFactory.getLogger(EmissionOffenseChecker.class);
    private final EmissionCalculator emissionCalculator;
    private final PropertiesConfig propertiesConfig;
    private final FineService fineService;

    @Autowired
    public EmissionOffenseChecker(EmissionCalculator emissionCalculator, CameraAdapter ca, LicensePlateAdapter lps, PropertiesConfig propertiesConfig, FineService fineService) {
        this.propertiesConfig = propertiesConfig;
        this.fineService = fineService;
        this.emissionCalculator = emissionCalculator;
        this.ca = ca;
        this.lps = lps;
    }


    @Override
    public void handleMessage(CameraMessage m) throws Exception {
        RetryTemplate rt = new RetryTemplate();
        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(propertiesConfig.getMaxretries());
        FixedBackOffPolicy bop = new FixedBackOffPolicy();
        bop.setBackOffPeriod(propertiesConfig.getRetrydelay());
        rt.setRetryPolicy(retryPolicy);
        rt.setBackOffPolicy(bop);
        rt.setThrowLastExceptionOnExhausted(true);
        rt.execute(new RetryCallback<Object, Exception>() {
            @Override
            public Object doWithRetry(RetryContext context) throws Exception {

                Camera emission = ca.AskInfo(m.getId());
                LicensePlateInfo perp = lps.askInfo(m.getLicensePlate());

                if (perp.getEuroNumber() < emission.getEuroNorm()) {
                    if (!fineService.checkfordoubles(m,propertiesConfig.getEmissionTime()).isPresent()) {
                        emissionCalculator.calculateFine(propertiesConfig.getEmissionfinefactor(), emission.getCameraId(), perp.getEuroNumber(), emission.getEuroNorm(), m.getTimestamp(),m.getLicensePlate(),perp.getNationalNumber());
                        LOGGER.info("auto: " + perp.getPlateId() + " has an emissionOffense.");

                    }
                }

                return null;
            }
        });
    }

    @Override
    public void onApplicationEvent(PropertiesChangeEvent event) {

    }
}
