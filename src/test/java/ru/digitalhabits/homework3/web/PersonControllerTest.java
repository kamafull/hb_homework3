package ru.digitalhabits.homework3.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import ru.digitalhabits.homework3.model.PersonRequest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class PersonControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void persons() throws Exception {
        // TODO: NotImplemented
        mockMvc.perform(get("/api/v1/persons")).andExpect(status().isOk());
    }

    @Test
    void person() throws Exception {
        // TODO: NotImplemented
        mockMvc.perform(get("/api/v1/persons/3")).andExpect(status().isOk());
    }

    @Test
    @Rollback
    @Transactional
    void createPerson() throws Exception {
        // TODO: NotImplemented
        PersonRequest personRequest = new PersonRequest();
        personRequest.setFirstName("das");
        personRequest.setLastName("sad");

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(personRequest);

        mockMvc.perform(post("/api/v1/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    @Rollback
    @Transactional
    void updatePerson() throws Exception {
        // TODO: NotImplemented
        PersonRequest personRequest = new PersonRequest();
        personRequest.setFirstName("111");
        personRequest.setLastName("222");

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(personRequest);

        mockMvc.perform(patch("/api/v1/persons/10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    @Rollback
    @Transactional
    void deletePerson() throws Exception {
        // TODO: NotImplemented
        mockMvc.perform(delete("/api/v1/persons/10")).andExpect(status().isOk());
    }
}