package ru.digitalhabits.homework3.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = DepartmentController.class)
class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void departments() {
        // TODO: NotImplemented
    }

    @Test
    void department() {
        // TODO: NotImplemented
    }

    @Test
    void createDepartment() {
        // TODO: NotImplemented
    }

    @Test
    void updateDepartment() {
        // TODO: NotImplemented
    }

    @Test
    void deleteDepartment() {
        // TODO: NotImplemented
    }

    @Test
    void addPersonToDepartment() {
        // TODO: NotImplemented
    }

    @Test
    void removePersonToDepartment() {
        // TODO: NotImplemented
    }

    @Test
    void closeDepartment() {
        // TODO: NotImplemented
    }
}