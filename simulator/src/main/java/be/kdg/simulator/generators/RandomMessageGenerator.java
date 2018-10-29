package be.kdg.simulator.generators;

import be.kdg.simulator.model.CameraMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Random;

/**
 * The RandomMessageGenerator will generate random CameraMessages.
 */
@Component()
@ConditionalOnProperty(name ="generator.type", havingValue = "random")
public class RandomMessageGenerator implements MessageGenerator{

    @Value("${maxId}")
    private int s;

    private Random r = new Random();

    @Override
    public CameraMessage generate() {

        int eersteNummer = r.nextInt(9)+1;
        StringBuilder letters = new StringBuilder();
        StringBuilder laatsteNummers = new StringBuilder();
        for (int i=0;i<3;i++) {

            int letter = r.nextInt(26)+'A';
            letters.append((char) letter);

            int randomNummer = r.nextInt(9)+1;
            laatsteNummers.append(randomNummer);

        }
        return new CameraMessage(r.nextInt(s),eersteNummer + "-" + letters + "-" + laatsteNummers, LocalDateTime.now(),1000);
    }
}
