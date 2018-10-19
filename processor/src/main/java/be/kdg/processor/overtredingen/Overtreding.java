package be.kdg.processor.overtredingen;

import be.kdg.processor.model.CameraMessage;

public interface Overtreding {
    void handleMessage(CameraMessage m);
}
