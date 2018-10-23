package be.kdg.simulator.posting.messengers;

import be.kdg.simulator.posting.serializers.XMLConverter;
import be.kdg.simulator.generators.MessageGenerator;
import be.kdg.simulator.model.CameraMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * The QueueMessenger is responsible for the posting of the CameraMessages to the RabbitMQ queue in XML format.
 */
@Component
@ConditionalOnProperty(name = "messenger.type", havingValue = "queue")
public class QueueMessenger implements Messenger {

    private final RabbitTemplate rabbitTemplate;
    private final MessageGenerator messageGenerator;

    public QueueMessenger( RabbitTemplate rabbitTemplate,MessageGenerator messageGenerator) {
        this.rabbitTemplate = rabbitTemplate;
        this.messageGenerator = messageGenerator;
    }



    @Override
    public void sendMessage(CameraMessage cm) {
        rabbitTemplate.setMessageConverter(new XMLConverter());
        rabbitTemplate.convertAndSend("CameraTopic","CM.test",cm);
    }
}
