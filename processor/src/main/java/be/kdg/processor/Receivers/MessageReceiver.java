package be.kdg.processor.Receivers;


import be.kdg.processor.deserializers.XMLConverter;
import be.kdg.processor.model.CameraMessage;
import be.kdg.processor.analysers.BoeteAnalyser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Klasse MessageReceiver haalt messages af van de queue
 */

@Component
public class MessageReceiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageReceiver.class);
    @Autowired
    private BoeteAnalyser ba;


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
        ba.checkEmissieOvertreding(message);
    }

}
