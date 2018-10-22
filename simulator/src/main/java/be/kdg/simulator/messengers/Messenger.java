package be.kdg.simulator.messengers;

import be.kdg.simulator.model.CameraMessage;

/**
 * De Messenger klasse is de interface voor verschillende messenging manieren (als er een nieuwe manier van messages zenden is moet dit geïmplementeerd worden)
 */
public interface Messenger {

    void sendMessage(CameraMessage cameraMessage);
}
