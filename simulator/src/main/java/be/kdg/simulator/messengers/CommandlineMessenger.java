package be.kdg.simulator.messengers;

import be.kdg.simulator.generators.MessageGenerator;
import be.kdg.simulator.model.CameraMessage;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
//oplossen met
// a) @Qualifier
// b) @ConditionalOnProperty

public class CommandlineMessenger implements Messenger {
    private final MessageGenerator messageGenerator;

    // Constructor Injection
    public CommandlineMessenger( @Qualifier("randomMessageGenerator") MessageGenerator messageGenerator) {
        this.messageGenerator = messageGenerator;
    }

    @Override
    public void sendMessage() {
        System.out.println(messageGenerator.generate().toString());
    }
}
