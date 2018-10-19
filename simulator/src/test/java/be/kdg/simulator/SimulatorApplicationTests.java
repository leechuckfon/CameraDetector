package be.kdg.simulator;

import be.kdg.simulator.generators.MessageGenerator;
import be.kdg.simulator.model.CameraMessage;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SimulatorApplicationTests {

    @Autowired
    // dit is Field Injection
    private MessageGenerator messageGenerator;


    @Test
    public void testMessageGenerator() {
        CameraMessage cameraMessage = messageGenerator.generate();
        Assert.assertTrue(cameraMessage.getLicensePlate().matches("[1-9]-[A-Z][A-Z][A-Z]-[1-9][1-9][1-9]$"));
    }

}
