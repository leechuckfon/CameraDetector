package be.kdg.simulator.generators;

import be.kdg.simulator.model.CameraMessage;
import org.springframework.stereotype.Component;

/**
 * MessageGenerator is de interface voor verschillende Generator klassen (als er een nieuwe manier is om messages te genereren zal dit geïmplementeerd moeten worden)
 */
@Component
public interface MessageGenerator {

    CameraMessage generate();
}
