package be.kdg.processor;

import be.kdg.processor.web.dto.UserCreationDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserApiTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserApiTests.class);

    @Test
    public void createTest(){
        UserCreationDTO udto = new UserCreationDTO("TestUsername","TestPassword",new String[]{"ADMIN"});
        String request = null;
        try {
            request = objectMapper.writeValueAsString(udto);
        } catch (JsonProcessingException e) {
            LOGGER.error("test couldn't transform DTO to Jsonstring.");
        }
        try {
            mockMvc.perform(MockMvcRequestBuilders.post("/usapi/createUser").contentType(MediaType.APPLICATION_JSON_UTF8).content(request)).andExpect(status().isCreated()).andDo(print()).andExpect(content().string(Matchers.containsString("TestUsername")));
        } catch (Exception e) {
            LOGGER.error("Test couldn't perform a post to usapi/createUser.");
        }
    }

    @Test
    public void updateTest(){
        UserCreationDTO udto = new UserCreationDTO("TestUsername","TestPassword",new String[]{"ADMIN"});
        String request = null;
        try {
            request = objectMapper.writeValueAsString(udto);
        } catch (JsonProcessingException e) {
            LOGGER.error("test couldn't transform createDTO to Jsonstring.");
        }
        try {
            mockMvc.perform(MockMvcRequestBuilders.post("/usapi/createUser").contentType(MediaType.APPLICATION_JSON_UTF8).content(request)).andExpect(status().isCreated()).andDo(print()).andExpect(content().string(Matchers.containsString("ROLE_ADMIN")));
        } catch (Exception e) {
            LOGGER.error("Test couldn't perform a post to usapi/createUser.");
        }

        UserCreationDTO updateDTO = new UserCreationDTO("TestUsername","NewPaassword",new String[]{"USER"});
        String updateRequest = null;
        try {
            updateRequest = objectMapper.writeValueAsString(updateDTO);
        } catch (JsonProcessingException e) {
            LOGGER.error("test couldn't transform updateDTO to Jsonstring.");
        }
        try {
            mockMvc.perform(MockMvcRequestBuilders.put("/usapi/updateUser").contentType(MediaType.APPLICATION_JSON_UTF8).content(updateRequest)).andExpect(status().isOk()).andDo(print()).andExpect(content().string(Matchers.containsString("ROLE_USER")));
        } catch (Exception e) {
            LOGGER.error("Test couldn't perform a put requset to usapi/createUser.");
        }
    }

    @Test
    public void readTest(){
        UserCreationDTO udto = new UserCreationDTO("TestUsername","TestPassword",new String[]{"ADMIN"});
        String request = null;
        try {
            request = objectMapper.writeValueAsString(udto);
        } catch (JsonProcessingException e) {
            LOGGER.error("test couldn't transform createDTO to Jsonstring.");
        }
        try {
            mockMvc.perform(MockMvcRequestBuilders.post("/usapi/createUser").contentType(MediaType.APPLICATION_JSON_UTF8).content(request)).andExpect(status().isCreated()).andDo(print()).andExpect(content().string(Matchers.containsString("ROLE_ADMIN")));
        } catch (Exception e) {
            LOGGER.error("Test couldn't perform a post to usapi/createUser.");
        }

        try {
            mockMvc.perform(MockMvcRequestBuilders.get("/usapi/user/TestUsername")).andExpect(status().isOk()).andDo(print()).andExpect(content().string(Matchers.containsString("ROLE_ADMIN"))).andExpect(content().string(Matchers.containsString("TestUsername")));
        } catch (Exception e) {
            LOGGER.error("Test couldn't perform a get request to usapi/user/TestUsername");
        }

    }

    @Test
    public void deleteTest(){
        UserCreationDTO udto = new UserCreationDTO("TestUsername","TestPassword",new String[]{"ADMIN"});
        String request = null;
        try {
            request = objectMapper.writeValueAsString(udto);
        } catch (JsonProcessingException e) {
            LOGGER.error("test couldn't transform createDTO to Jsonstring.");
        }
        try {
            mockMvc.perform(MockMvcRequestBuilders.post("/usapi/createUser").contentType(MediaType.APPLICATION_JSON_UTF8).content(request)).andExpect(status().isCreated()).andDo(print()).andExpect(content().string(Matchers.containsString("ROLE_ADMIN")));
        } catch (Exception e) {
            LOGGER.error("Test couldn't perform a post to usapi/createUser.");
        }

        try {
            mockMvc.perform(MockMvcRequestBuilders.post("/usapi/deleteUser/TestUsername")).andExpect(status().isOk()).andExpect(content().string(Matchers.containsString("1")));
        } catch (Exception e) {
            LOGGER.error("Test couldn't perform a get request to usapi/deleteUser/TestUsername");
        }
    }
}
