package be.kdg.simulator;


import be.kdg.simulator.generators.RandomMessageGenerator;
import be.kdg.simulator.receivers.MessageReceiver;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import javax.jms.Topic;

@SpringBootApplication
@EnableScheduling
public class SimulatorApplication {



    //**LOCAL**
//    static final String tpocExchangeName = "cameraTopic";
//
//    static final String queueName = "CMQueue";
//
//
//    @Bean
//    Queue queue() {
//        return new Queue(queueName,false);
//    }
//
//    @Bean
//    TopicExchange exchange() {
//        return new TopicExchange(tpocExchangeName);
//    }
//
//    @Bean
//    Binding binding(Queue queue, TopicExchange exchange) {
//        return BindingBuilder.bind(queue).to(exchange).with("example.Key.1");
//    }
//    @Bean
//    SimpleMessageListenerContainer container(ConnectionFactory cF, MessageListenerAdapter lA) {
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
//        container.setConnectionFactory(cF);
//        container.setQueueNames(queueName);
//        container.setMessageListener(lA);
//        return container;
//    }
//
//    @Bean
//    MessageListenerAdapter listenerAdapter(MessageReceiver mr) {
//        return new MessageListenerAdapter(mr,"receiveMessage");
//    }

    public static void main(String[] args) {
        //ConnectionFactory naar Cloud server
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("raven-01.rmq.cloudamqp.com");
        connectionFactory.setUsername("riggewbl");
        connectionFactory.setPassword("WuEkK5q-9lgc4xGeATUzOxL_cEP8A_gw");
        connectionFactory.setVirtualHost("riggewbl");
        //settings voor het connecten
        // tijd tussen messages(?)
        connectionFactory.setRequestedHeartBeat(30);
        //timeout na millis
        connectionFactory.setConnectionTimeout(30000);
        //ConnectionFactory zal in gebruik worden gezet
        RabbitAdmin admin = new RabbitAdmin(connectionFactory);
        //nieuwe Queue aanmaken
        Queue q = new Queue("CMQueue");
        //Queue van admin zetten
        admin.declareQueue(q);
        //exchange aanmaken
        TopicExchange exchange = new TopicExchange("CameraTopic");
        //exchange van admin zetten
        admin.declareExchange(exchange);
        //binding tussen queue en exchange aanmaken (deze queue is voor deze topic)
        //admin = groot bedrijf
        //exchange = een evenement
        //queue = wachtrij
        // Wachtrij zal voor een evenement zijn. Groot bedrijf zal zorgen dat de juiste queue naar de juiste evenement wijst
        // en dat de twee wachtrijen voor verschillende evenementen niet zullen mengen.
        // administratief makkelijker.
        admin.declareBinding(BindingBuilder.bind(q).to(exchange).with("foo.test"));
        // container voor het luisteren naar connectionfactory voor messages
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        // handelt messages af
        MessageReceiver me = new MessageReceiver();
        // message listeneradapter als wrapper voor message handler, die in container moet
        MessageListenerAdapter adapter = new MessageListenerAdapter(me);
        // wordt in container geplaatst
        container.setMessageListener(adapter);
        // queues van admin worden bepaald
        container.setQueueNames("CMQueue");
        // start met luisteren
        container.start();

        RabbitTemplate rt = new RabbitTemplate(connectionFactory);
        RandomMessageGenerator rmg = new RandomMessageGenerator();
        rt.convertAndSend("CameraTopic","foo.test",rmg.generate().toString());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
         container.stop();
//        SpringApplication.run(SimulatorApplication.class, args);

    }

}
