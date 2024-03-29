package be.kdg.processor;

import be.kdg.processor.receiving.overtredingen.EmissionOffenseChecker;
import be.kdg.processor.model.CameraMessage;
import be.kdg.sa.services.CameraNotFoundException;
import be.kdg.sa.services.CameraServiceProxy;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProcessorApplicationTests {
    private CameraServiceProxy csp = new CameraServiceProxy();
    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessorApplicationTests.class);
    @Autowired
    private EmissionOffenseChecker emissionOffenseChecker;
    @Autowired
    private MockMvc mockMvc;


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

    @Test
    public void emissieDetectieTest() throws Exception {
       emissionOffenseChecker.handleMessage(new CameraMessage(3,"1-ABC-123", LocalDateTime.now()));
       mockMvc.perform(MockMvcRequestBuilders.get("/api/fines")).andDo(print()).andExpect(content().string(containsString("\"cameraId\":3")));
    }

}
