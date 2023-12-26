package ru.digitalhabits.homework3.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import ru.digitalhabits.homework3.model.DepartmentRequest;
import ru.digitalhabits.homework3.model.PersonRequest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class DepartmentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void departments() throws Exception {
        // TODO: NotImplemented
        mockMvc.perform(get("/api/v1/departments")).andExpect(status().isOk());
    }

    @Test
    void department() throws Exception {
        // TODO: NotImplemented
        mockMvc.perform(get("/api/v1/departments/1")).andExpect(status().isOk());
    }

    @Test
    @Rollback
    @Transactional
    void createDepartment() throws Exception {
        // TODO: NotImplemented
        DepartmentRequest departmentRequest = new DepartmentRequest();
        departmentRequest.setName("das");

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(departmentRequest);

        mockMvc.perform(post("/api/v1/departments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    @Rollback
    @Transactional
    void updateDepartment() throws Exception {
        // TODO: NotImplemented
        DepartmentRequest departmentRequest = new DepartmentRequest();
        departmentRequest.setName("111");

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(departmentRequest);

        mockMvc.perform(patch("/api/v1/departments/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    @Rollback
    @Transactional
    void deleteDepartment() throws Exception {
        // TODO: NotImplemented
        mockMvc.perform(delete("/api/v1/departments/1")).andExpect(status().isOk());
    }

    @Test
    @Rollback
    @Transactional
    void addPersonToDepartment() throws Exception {
        // TODO: NotImplemented
        mockMvc.perform(post("/api/v1/departments/1/10")).andExpect(status().isOk());
    }

    @Test
    @Rollback
    @Transactional
    void removePersonToDepartment() throws Exception {
        // TODO: NotImplemented
        mockMvc.perform(delete("/api/v1/departments/1/10")).andExpect(status().isOk());
    }

    @Test
    @Rollback
    @Transactional
    void closeDepartment() throws Exception {
        // TODO: NotImplemented
        mockMvc.perform(post("/api/v1/departments/1/close")).andExpect(status().isOk());
    }
}