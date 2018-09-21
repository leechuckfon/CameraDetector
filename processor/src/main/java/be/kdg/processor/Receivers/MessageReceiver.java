package be.kdg.processor.Receivers;

import be.kdg.processor.model.CameraMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class MessageReceiver implements Receiver {
    @Override
    @JmsListener(destination = "test")
    public void ReceiveMessage(CameraMessage cm) {
        System.out.println(cm.toString());
    }


}
