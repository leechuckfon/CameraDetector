package be.kdg.simulator.messengers;

import be.kdg.simulator.generators.MessageGenerator;
import be.kdg.simulator.model.CameraMessage;
import javafx.scene.Camera;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
//oplossen met
// a) @Qualifier
// b) @ConditionalOnProperty
@ConditionalOnProperty(value = "messenger.type",havingValue = "commandline")
public class CommandlineMessenger implements Messenger {

    @Override
    public void sendMessage(CameraMessage cameraMessage) {
        if (  cameraMessage != null) {
            System.out.println(cameraMessage.toString());
        } else {
            System.exit(0);

        }
    }
}
