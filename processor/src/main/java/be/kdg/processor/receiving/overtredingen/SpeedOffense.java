package be.kdg.processor.receiving.overtredingen;

import be.kdg.processor.receiving.adapters.CameraAdapter;
import be.kdg.processor.receiving.adapters.LicensePlateAdapter;
import be.kdg.processor.model.CameraMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
public class SpeedOffense {

    private final CameraAdapter ca;
    private final LicensePlateAdapter lps;
    private static final Logger LOGGER = LoggerFactory.getLogger(SpeedOffense.class);
    private List<CameraMessage> bufferedMessages = new ArrayList<>();
    private Map<String,List<CameraMessage>> beginningEnd;
    private final long delay;


    @Autowired
    public SpeedOffense(@Value("${timeframeSnelheid}") long delay, CameraAdapter ca, LicensePlateAdapter lps) {
        this.delay = delay;
        this.ca = ca;
        this.lps = lps;
    }

    //buffer de messages
    public void handleMessage(CameraMessage m) throws Exception {
        if (m != null) {
            bufferedMessages.add(m);
        }
        checkOffense();
    }

    @Scheduled(fixedDelay = 60000L)
    public void deleteBufferedMessages() {
        List<CameraMessage> matchedMessages =  bufferedMessages.stream().filter((x) -> x.getTimestamp().until(LocalDateTime.now(),ChronoUnit.MILLIS) > delay).collect(Collectors.toList());
        for (CameraMessage matchedMessage : matchedMessages) {
            bufferedMessages.remove(matchedMessage);
        }
    }

    private void checkOffense() throws Exception {
        beginningEnd = new HashMap<>();
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

