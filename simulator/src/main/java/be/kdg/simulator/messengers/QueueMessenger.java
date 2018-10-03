package be.kdg.simulator.messengers;

import be.kdg.simulator.config.XMLConverter;
import be.kdg.simulator.generators.MessageGenerator;
import be.kdg.simulator.Receivers.MessageReceiver;
import be.kdg.simulator.model.CameraMessage;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MarshallingMessageConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
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
