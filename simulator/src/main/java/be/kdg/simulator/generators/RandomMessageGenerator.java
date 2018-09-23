package be.kdg.simulator.generators;

import be.kdg.simulator.model.CameraMessage;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component()
@ConditionalOnProperty(name ="generator.type", havingValue = "random")
public class RandomMessageGenerator implements MessageGenerator{

    @Override
    public CameraMessage generate() {
        //TODO: Finish this method

        return new CameraMessage(1,"1-ABC-123", LocalDateTime.now());
    }
}
