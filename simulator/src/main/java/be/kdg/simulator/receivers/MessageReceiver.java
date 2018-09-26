package be.kdg.simulator.Receivers;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Klasse MessageReceiver haalt messages af van de queue
 */

@Component
public class MessageReceiver {

    public void receiveMessage(String message) {
        System.out.println("Received:" +message);
    }

}
