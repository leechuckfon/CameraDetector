package be.kdg.simulator.messengers;

import be.kdg.simulator.generators.MessageGenerator;
import be.kdg.simulator.model.CameraMessage;
import ch.qos.logback.core.util.FixedDelay;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
//oplossen met
// a) @Qualifier
// b) @ConditionalOnProperty
public class CommandlineMessenger implements Messenger {

    private final MessageGenerator messageGenerator;

    // Constructor Injection
    public CommandlineMessenger(MessageGenerator messageGenerator) {
        this.messageGenerator = messageGenerator;
    }

    @Override
    @Scheduled(fixedDelay = 1000L)
    public void sendMessage() {
        System.out.println(messageGenerator.generate().toString());
    }
}
