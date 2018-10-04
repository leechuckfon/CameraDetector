package be.kdg.processor;

import be.kdg.processor.model.Camera;
import be.kdg.processor.model.CameraDeserializer;
import be.kdg.sa.services.CameraNotFoundException;
import be.kdg.sa.services.CameraServiceProxy;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProcessorApplicationTests {
    private CameraServiceProxy csp = new CameraServiceProxy();
    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessorApplicationTests.class);

    @Test
    public void cameraServiceTest() {
        try {
            Assert.assertNotNull(csp.get(1));
        } catch (IOException |CameraNotFoundException e) {
            if (e.getClass() == IOException.class) {
                LOGGER.error("IOException is opgetreden tijdens het ophalen van een camera.");
            }
            if (e.getClass() == CameraNotFoundException.class) {
                LOGGER.error("Camera is niet gevonden");
            }
        }

    }

}
