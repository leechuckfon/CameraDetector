package be.kdg.processor.receiving.overtredingen;

import be.kdg.processor.model.CameraMessage;

import java.io.IOException;

/**
 * Interface that has to be implemented for each OffenseChecker.
 * If there is a new offense there has to be a new OffenseChecker that implements this interface.
 */
public interface OffenseChecker {
    void handleMessage(CameraMessage m) throws IOException, Exception;
}
