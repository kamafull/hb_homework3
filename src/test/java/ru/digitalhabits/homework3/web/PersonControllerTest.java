package ru.digitalhabits.homework3.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = PersonController.class)
class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void persons() {
        // TODO: NotImplemented
    }

    @Test
    void person() {
        // TODO: NotImplemented
    }

    @Test
    void createPerson() {
        // TODO: NotImplemented
    }

    @Test
    void updatePerson() {
        // TODO: NotImplemented
    }

    @Test
    void deletePerson() {
        // TODO: NotImplemented
    }
}