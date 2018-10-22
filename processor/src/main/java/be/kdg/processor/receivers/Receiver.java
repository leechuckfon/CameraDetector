package be.kdg.processor.receivers;

import be.kdg.processor.model.CameraMessage;

/**
 * Interface voor een Receiver, als er een nieuwe Receiver moet aangemaakt worden moet deze dit implementeren
 */
public interface Receiver {
    void ReceiveMessage(CameraMessage cm);
}
