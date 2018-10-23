package be.kdg.simulator.generators;

import be.kdg.simulator.model.CameraMessage;
import org.springframework.stereotype.Component;

/**
 * MessageGenerator is the interface which needs to be implemented for each way that there is to generate a message
 */
@Component
public interface MessageGenerator {

    CameraMessage generate();
}
