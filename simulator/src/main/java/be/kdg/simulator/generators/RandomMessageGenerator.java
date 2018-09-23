package be.kdg.simulator.generators;

import be.kdg.simulator.model.CameraMessage;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Random;

@Component()
@ConditionalOnProperty(name ="generator.type", havingValue = "random")
public class RandomMessageGenerator implements MessageGenerator{

    private Random r = new Random();

    @Override
    public CameraMessage generate() {
        //TODO: Randomize nummerplaat

        int eersteNummer = r.nextInt(9)+1;
        String letters = "";
        String laatsteNummers = "";
        for (int i=0;i<3;i++) {

            int letter = r.nextInt(26)+'A';
            letters += (char) letter;

            int randomNummer = r.nextInt(9)+1;
            laatsteNummers += randomNummer;

        }
        return new CameraMessage(eersteNummer + "-" + letters + "-" + laatsteNummers, LocalDateTime.now());
    }
}
