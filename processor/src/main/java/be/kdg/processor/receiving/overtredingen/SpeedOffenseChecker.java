package be.kdg.processor.receiving.overtredingen;

import be.kdg.processor.model.CameraMessage;
import be.kdg.processor.model.events.PropertiesChangeEvent;
import be.kdg.processor.model.fine.calculcators.SpeedCalculator;
import be.kdg.processor.model.licenseplate.LicensePlateInfo;
import be.kdg.processor.receiving.adapters.CameraAdapter;
import be.kdg.processor.receiving.adapters.LicensePlateAdapter;
import be.kdg.processor.receiving.configs.PropertiesConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Chuck Fon Lee
 * The SpeedOffense class will buffer messages and check for an offense every time a message arrives.
 * If the messages in the buffer are too old, then these will get deleted.
 */

@Component
public class SpeedOffenseChecker implements ApplicationListener<PropertiesChangeEvent>, OffenseChecker {

    private final CameraAdapter ca;
    private final LicensePlateAdapter lps;
    private static final Logger LOGGER = LoggerFactory.getLogger(SpeedOffenseChecker.class);
    private List<CameraMessage> bufferedMessages = new ArrayList<>();
    private final SpeedCalculator speedCalculator;
    private PropertiesConfig propertiesConfig;


    @Autowired
    public SpeedOffenseChecker(CameraAdapter ca, LicensePlateAdapter lps, SpeedCalculator speedCalculator, PropertiesConfig propertiesConfig) {
        this.propertiesConfig = propertiesConfig;
        this.ca = ca;
        this.lps = lps;
        this.speedCalculator = speedCalculator;
    }

    public void handleMessage(CameraMessage m) throws Exception {
        if (m != null) {
            bufferedMessages.add(m);
        }

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
                    checkOffense();
                    return null;
                }
            });
    }

    @Scheduled(fixedDelay = 60000L)
    public void deleteBufferedMessages() {
        List<CameraMessage> matchedMessages = bufferedMessages.stream().filter((x) -> x.getTimestamp().until(LocalDateTime.now(), ChronoUnit.MILLIS) > propertiesConfig.getTimeframeSnelheid()).collect(Collectors.toList());
        for (CameraMessage matchedMessage : matchedMessages) {
            bufferedMessages.remove(matchedMessage);
        }
    }

    private void checkOffense() throws Exception {
        Map<String, List<CameraMessage>> beginningEnd = new HashMap<>();
        for (CameraMessage bufferedMessage : bufferedMessages) {
            if (!(beginningEnd.containsKey(bufferedMessage.getLicensePlate()))) {
                beginningEnd.put(bufferedMessage.getLicensePlate(), new ArrayList<>());
            }
            beginningEnd.get(bufferedMessage.getLicensePlate()).add(bufferedMessage);
        }

        for (String s : beginningEnd.keySet()) {
            if (beginningEnd.get(s).size() > 1) {
                CameraMessage begin = beginningEnd.get(s).get(0);
                CameraMessage eind = beginningEnd.get(s).get(1);
                LicensePlateInfo info =   lps.askInfo(begin.getLicensePlate());
                int max_snelheid = ca.AskInfo(begin.getId()).getSegment().getSpeedLimit();
                LocalDateTime b = begin.getTimestamp();
                LocalDateTime e = eind.getTimestamp();
                int afstand = ca.AskInfo(begin.getId()).getSegment().getDistance();
                float millis = (b.until(e, ChronoUnit.MILLIS));
                float speed = ((float) afstand / 1000) / (millis / 3600000);
                if (speed > max_snelheid) {
                    /* maak fine */
                    LOGGER.info(begin.getLicensePlate() + " heeft een snelheidsboete.");
                    speedCalculator.calculateFine(propertiesConfig.getSpeedfinefactor(), eind.getId(), Math.round(speed), max_snelheid, eind.getTimestamp(),begin.getLicensePlate(),info.getNationalNumber());
                    bufferedMessages.removeAll(bufferedMessages.stream().filter(x -> x.getLicensePlate().equals(s)).collect(Collectors.toList()));
                }
            }
        }

    }


    @Override
    public void onApplicationEvent(PropertiesChangeEvent event) {
        this.propertiesConfig = (PropertiesConfig) event.getSource();
    }
}

