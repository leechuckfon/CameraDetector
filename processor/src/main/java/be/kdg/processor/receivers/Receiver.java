package be.kdg.processor.receivers;

import be.kdg.processor.model.CameraMessage;

public interface Receiver {
    void ReceiveMessage(CameraMessage cm);
}
