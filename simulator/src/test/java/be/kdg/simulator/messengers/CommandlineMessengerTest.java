package be.kdg.simulator.messengers;

import be.kdg.simulator.generators.MessageGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommandlineMessengerTest {

    @Autowired
    private Messenger messenger;
    @Autowired
    private MessageGenerator gen;

    @Test
    public void sendMessage() {
        messenger.sendMessage(gen.generate());
    }
}