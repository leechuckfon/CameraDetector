package be.kdg.simulator.messengers;

import be.kdg.simulator.serializers.XMLConverter;
import be.kdg.simulator.generators.MessageGenerator;
import be.kdg.simulator.Receivers.MessageReceiver;
import be.kdg.simulator.model.CameraMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "messenger.type", havingValue = "queue")
public class QueueMessenger implements Messenger {

    private final RabbitTemplate rabbitTemplate;
    private final MessageGenerator messageGenerator;

    public QueueMessenger(MessageReceiver receiver, RabbitTemplate rabbitTemplate,MessageGenerator messageGenerator) {
        MessageReceiver receiver1 = receiver;
        this.rabbitTemplate = rabbitTemplate;
        this.messageGenerator = messageGenerator;
    }



    @Override
    public void sendMessage(CameraMessage cm) {
        rabbitTemplate.setMessageConverter(new XMLConverter());
        rabbitTemplate.convertAndSend("CameraTopic","CM.test",cm);
    }
}
