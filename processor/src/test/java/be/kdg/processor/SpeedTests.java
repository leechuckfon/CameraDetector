package be.kdg.processor;

import be.kdg.processor.model.CameraMessage;
import be.kdg.processor.receiving.overtredingen.SpeedOffenseChecker;
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

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SpeedTests {
    private static final Logger LOGGER = LoggerFactory.getLogger(SpeedTests.class);
    @Autowired
    private SpeedOffenseChecker speedOffenseChecker;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void detectionSpeedOffenseTest() throws Exception {
        speedOffenseChecker.handleMessage(new CameraMessage(4,"123-Test-456", LocalDateTime.now()));
        speedOffenseChecker.handleMessage(new CameraMessage(5,"123-Test-456", LocalDateTime.now().plus(1000, ChronoUnit.MILLIS)));

        try {
            mockMvc.perform(MockMvcRequestBuilders.get("/api/fines")).andDo(print()).andExpect(content().string(containsString("SPEED")));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
