package be.kdg.processor.receiving.overtredingen;

import be.kdg.processor.model.CameraMessage;

import java.io.IOException;

/**
 * Interface that has to be implemented for each Offense.
 */
public interface Offense {
    void handleMessage(CameraMessage m) throws IOException;
}
