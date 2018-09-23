package be.kdg.simulator.generators;

import be.kdg.simulator.model.CameraMessage;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "generator.type", havingValue = "file")
public class FileGenerator implements MessageGenerator{

    @Override
    public CameraMessage generate() {
        return null;
    }
}
