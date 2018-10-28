package be.kdg.processor;

import be.kdg.processor.model.fine.FineType;
import be.kdg.processor.model.fine.Fine;
import be.kdg.processor.web.services.FineService;
import org.hamcrest.Matchers;
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

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ServiceTests {
    @Autowired
    private FineService fineService;
    @Autowired
    private MockMvc mockMvc;
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceTests.class);


    @Test
    public void serviceTest() {
        /* verander naar de calculator */
        Assert.assertNotNull(fineService.saveFine(new Fine(FineType.EMISSION,50,1,"OvertredingTest", LocalDateTime.now(),"1-AME-123")));
        try {
            mockMvc.perform(MockMvcRequestBuilders.get("/api/fine/getall")).andDo(print()).andExpect(content().string(Matchers.containsString("OvertredingTest")));
        } catch (Exception e) {
            LOGGER.error("Geen boetes gevonden");
        }

    }

}
