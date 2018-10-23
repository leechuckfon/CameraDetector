package be.kdg.simulator.posting.messengers;

import be.kdg.simulator.model.CameraMessage;

/**
 * The Messenger class is the interface that has to be implemented for each way you can send a message from the Simulator to the Processor.
 */
public interface Messenger {

    void sendMessage(CameraMessage cameraMessage);
}
