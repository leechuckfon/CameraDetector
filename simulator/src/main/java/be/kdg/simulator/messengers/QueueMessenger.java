package be.kdg.simulator.messengers;

import be.kdg.simulator.generators.RandomMessageGenerator;
import be.kdg.simulator.model.CameraMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;

public class QueueMessenger implements Messenger {
    @Override
    public void sendMessage() {

    }
}
