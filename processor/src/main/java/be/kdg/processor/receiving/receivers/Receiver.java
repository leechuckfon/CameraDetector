package be.kdg.processor.receiving.receivers;

import be.kdg.processor.model.CameraMessage;

/**
 * Interface for a receiver, if there are new receiving methods then this interface needs to be implemented
 */
public interface Receiver {
    void ReceiveMessage(CameraMessage cm);
}
