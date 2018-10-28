package be.kdg.processor;

import be.kdg.processor.model.fine.FineType;
import be.kdg.processor.model.fine.Fine;
import be.kdg.processor.web.services.FineService;
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
    private FineService fineService;

    @Test
    public void restTest() throws Exception {
        fineService.saveFine(new Fine(FineType.EMISSION,1000,1,"restTest", LocalDateTime.now(),"2-ABV-357"));
        mockMvc.perform(put("/api/approvefine/1")).andExpect(status().isAccepted()).andDo(print()).andExpect(content().string(containsString("\"approved\":true")));

    }

    @Test
    public void mvcTest() throws Exception {
            mockMvc.perform(get("/web/finefactors"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("showboetefactoren"));
    }

}

