package be.kdg.simulator.messengers;

import be.kdg.simulator.generators.MessageGenerator;
import be.kdg.simulator.generators.RandomMessageGenerator;
import be.kdg.simulator.model.CameraMessage;
import be.kdg.simulator.Receivers.MessageReceiver;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "enableQueue", havingValue = "true")
public class QueueMessenger implements Messenger {

    private final RabbitTemplate rabbitTemplate;
    private final MessageReceiver receiver;
    private final MessageGenerator messageGenerator;

    public QueueMessenger(MessageReceiver receiver, RabbitTemplate rabbitTemplate,MessageGenerator messageGenerator) {
        this.receiver = receiver;
        this.rabbitTemplate = rabbitTemplate;
        this.messageGenerator = messageGenerator;
    }

    @Bean
    Queue queue() {
        return new Queue("CMQueue", false);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange("CameraTopic");
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("CM.*");
    }


    @Override
    public void sendMessage() {

        rabbitTemplate.convertAndSend("CameraTopic","CM.test",messageGenerator.generate().toString());


    }
}
