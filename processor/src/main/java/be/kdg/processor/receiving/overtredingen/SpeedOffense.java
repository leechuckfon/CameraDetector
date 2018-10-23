package be.kdg.processor.receiving.overtredingen;

import be.kdg.processor.receiving.adapters.CameraAdapter;
import be.kdg.processor.receiving.adapters.LicensePlateAdapter;
import be.kdg.processor.model.CameraMessage;
import be.kdg.sa.services.CameraNotFoundException;
import be.kdg.sa.services.LicensePlateNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
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
public class SpeedOffense {

    @Autowired
    private CameraAdapter ca;
    @Autowired
    private LicensePlateAdapter lps;

    private static final Logger LOGGER = LoggerFactory.getLogger(SpeedOffense.class);
    private List<CameraMessage> bufferedMessages = new ArrayList<>();
    private final long delay;


    @Autowired
    public SpeedOffense(@Value("${timeframeSnelheid}") long delay) {
        this.delay = delay;

    }

    //buffer de messages
    @Retryable(
            maxAttempts = 3,
            backoff = @Backoff(delay = 1000),
            value ={IOException.class, CameraNotFoundException.class, LicensePlateNotFoundException.class}
    )
    public void handleMessage(CameraMessage m) throws Exception {
        if (m != null) {
            bufferedMessages.add(m);
        }
        checkOffense();
    }

    @Recover
    public void printException(Exception e) {
        LOGGER.error(e.getMessage());
    }

    @Scheduled(fixedDelay = 60000L)
    public void deleteBufferedMessages() {
        List<CameraMessage> matchedMessages =  bufferedMessages.stream().filter((x) -> x.getTimestamp().until(LocalDateTime.now(),ChronoUnit.MILLIS) > delay).collect(Collectors.toList());
        for (CameraMessage matchedMessage : matchedMessages) {
            bufferedMessages.remove(matchedMessage);
        }
    }

    private void checkOffense() throws Exception {
        Map<String, List<CameraMessage>> beginningEnd = new HashMap<>();
        for (CameraMessage bufferedMessage : bufferedMessages) {
            if(!(beginningEnd.containsKey(bufferedMessage.getLicensePlate()))){
                beginningEnd.put(bufferedMessage.getLicensePlate(),new ArrayList<>());
            }
                beginningEnd.get(bufferedMessage.getLicensePlate()).add(bufferedMessage);

        }

        for (String s : beginningEnd.keySet()) {
            if (beginningEnd.get(s).size() > 1) {
                CameraMessage begin = beginningEnd.get(s).get(0);
                CameraMessage eind = beginningEnd.get(s).get(1);
                int max_snelheid = ca.AskInfo(begin.getId()).getSegment().getSpeedLimit();
                LocalDateTime b = begin.getTimestamp();
                LocalDateTime e = eind.getTimestamp();
                int afstand = ca.AskInfo(begin.getId()).getSegment().getDistance();
                float millis = (b.until(e, ChronoUnit.MILLIS));
                float speed = ((float) afstand/1000) / (millis / 360000);
                if (speed > max_snelheid) {
                    /* maak fine */
                    bufferedMessages.removeAll(bufferedMessages.stream().filter(x -> x.getLicensePlate().equals(s)).collect(Collectors.toList()));
                    System.out.println(speed);
                    System.out.println(s + " heeft een fine voor te snel rijden.");
                }
            }
        }

    }
}

