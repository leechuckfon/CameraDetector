package be.kdg.processor;

import be.kdg.processor.model.boete.BoeteTypes;
import be.kdg.processor.model.boete.Boete;
import be.kdg.processor.services.BoeteService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RestWebTests {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestWebTests.class);
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private BoeteService boeteService;

    @Test
    public void restTest() {
        boeteService.saveBoete(new Boete(BoeteTypes.EMISSIE,1000,1,"restTest", LocalDateTime.now()));
        try {
            mockMvc.perform(put("/api/boetegoedkeuring/1")).andExpect(status().isAccepted()).andDo(print()).andExpect(content().string(containsString("\"goedgekeurd\":true")));
        } catch (Exception e) {
            LOGGER.error("restTest gaf een error");
        }
    }

    @Test
    public void mvcTest() {
        try {
            mockMvc.perform(get("/web/boetefactoren"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("showboetefactoren"));
        } catch (Exception e) {
            LOGGER.error("MVCTest gaf een error");
        }
    }

}

