package be.kdg.processor.buffers;

import be.kdg.processor.model.CameraMessage;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class MessageBuffer {
    private ArrayList<CameraMessage> bufferedMessages;

    public MessageBuffer() {
        this.bufferedMessages = new ArrayList<>();
    }


    public void addMessage(CameraMessage m) {
        bufferedMessages.add(m);
    }


}
