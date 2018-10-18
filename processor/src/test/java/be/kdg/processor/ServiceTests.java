package be.kdg.processor;

import be.kdg.processor.model.boete.BOETETYPES;
import be.kdg.processor.model.boete.Boete;
import be.kdg.processor.services.BoeteService;
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
    private BoeteService boeteService;
    @Autowired
    private MockMvc mockMvc;
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceTests.class);


    @Test
    public void serviceTest() {
        Assert.assertNotNull(boeteService.saveBoete(new Boete(BOETETYPES.EMISSIE,50,1,"OvertredingTest", LocalDateTime.now())));
        try {
            mockMvc.perform(MockMvcRequestBuilders.get("/api/boete/getall")).andDo(print()).andExpect(content().string(Matchers.containsString("OvertredingTest")));
        } catch (Exception e) {
            LOGGER.error("Geen boetes gevonden");
        }

    }

}
