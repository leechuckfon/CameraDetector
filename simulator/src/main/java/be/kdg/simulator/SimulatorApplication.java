package be.kdg.simulator;

import be.kdg.simulator.generators.RandomMessageGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.core.JmsTemplate;

@SpringBootApplication
public class SimulatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimulatorApplication.class, args);
//        JmsTemplate jmsTemplate = new JmsTemplate();
//        jmsTemplate.convertAndSend("test",new RandomMessageGenerator().generate());
    }
}
