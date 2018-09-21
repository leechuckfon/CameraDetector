package be.kdg.processor.Receivers;

import be.kdg.processor.model.CameraMessage;

public interface Receiver {
    void ReceiveMessage(CameraMessage cm);
}
