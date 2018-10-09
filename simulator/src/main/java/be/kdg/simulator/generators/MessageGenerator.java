package be.kdg.simulator.generators;

import be.kdg.simulator.model.CameraMessage;
import org.springframework.stereotype.Component;

@Component
public interface MessageGenerator {

    CameraMessage generate();
}
