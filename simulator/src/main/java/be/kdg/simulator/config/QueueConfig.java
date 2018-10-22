package be.kdg.simulator.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * De QueueConfig zijn de queue configuraties van de RabbitMQ queue.
 */
@Configuration
public class QueueConfig {

    @Bean
    Queue queue() {
        return new Queue("CMQueue", true);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange("CameraTopic");
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("CM.*");
    }
}
