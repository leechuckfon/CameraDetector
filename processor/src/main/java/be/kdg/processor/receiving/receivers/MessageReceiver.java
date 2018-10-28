package be.kdg.processor.receiving.receivers;


import be.kdg.processor.receiving.deserializers.XMLConverter;
import be.kdg.processor.model.CameraMessage;
import be.kdg.processor.receiving.analysers.FineAnalyser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * The MessageReceiver class will get messages off the queue and send these to the FineAnalyser object.
 */

@Component
public class MessageReceiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageReceiver.class);
    @Autowired
    private FineAnalyser ba;

    @Bean
    private SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames("CMQueue");
        listenerAdapter.setMessageConverter(new XMLConverter());
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    private MessageListenerAdapter listenerAdapter(MessageReceiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

    public void receiveMessage(CameraMessage message) {
        LOGGER.info("Message received: " + message.toString());
        ba.checkOffenses(message);
    }

}
