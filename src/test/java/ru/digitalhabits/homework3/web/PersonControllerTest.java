package ru.digitalhabits.homework3.web;

import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.digitalhabits.homework3.domain.Department;
import ru.digitalhabits.homework3.domain.Person;
import ru.digitalhabits.homework3.model.person.PersonFullResponse;
import ru.digitalhabits.homework3.model.person.PersonRequest;
import ru.digitalhabits.homework3.model.person.PersonShortResponse;
import ru.digitalhabits.homework3.service.PersonService;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PersonController.class)
class PersonControllerTest {

    private final static int LIST_SIZE = 5;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    PersonService personService;

    private List<PersonShortResponse> personShortResponses;
    private List<PersonFullResponse> personFullResponses;

    @Test
    void injectedComponentsAreNotNull() {
        assertThat(mockMvc).isNotNull();
        assertThat(personService).isNotNull();
    }

    @BeforeEach
    void setUp() {
        personShortResponses = new ArrayList<>(LIST_SIZE);
        personFullResponses = new ArrayList<>(LIST_SIZE);

        for (int i = 1; i < LIST_SIZE + 1; i++) {
            Department department = new Department()
                    .setId(i)
                    .setName("NameDepartment" + i);

            Person person = new Person()
                    .setId(i)
                    .setFirstName("firstName" + i)
                    .setLastName("lastName" + i)
                    .setAge(25 + i)
                    .setDepartment(department);

            department.setPersons(List.of(person));

            personShortResponses.add(new PersonShortResponse(person));
            personFullResponses.add(new PersonFullResponse(person));
        }
    }

    @Test
    void persons200() throws Exception {
        when(personService.findAll()).thenReturn(personShortResponses);

        final String expectedJson = gson.toJson(personShortResponses);

        MvcResult result = mockMvc.perform(get("/api/v1/persons")
                        .content(expectedJson)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String actualJson = result.getResponse().getContentAsString();

        Assertions.assertEquals(expectedJson, actualJson);
    }

    @Test
    void personsIfEmpty() throws Exception {
        when(personService.findAll()).thenReturn(Collections.emptyList());

        final String expectedJson = gson.toJson(Collections.emptyList());

        MvcResult result = mockMvc.perform(get("/api/v1/persons")
                        .content(expectedJson)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String actualJson = result.getResponse().getContentAsString();

        Assertions.assertEquals(expectedJson, actualJson);
    }

    @Test
    void person200() {
        personFullResponses.stream()
                .limit(1)
                .forEach(person -> {
                    try {
                        when(personService.getById(anyInt())).thenReturn(person);

                        final String expectedJson = gson.toJson(person);

                        MvcResult result = mockMvc.perform(get("/api/v1/persons/{id}", anyInt())
                                        .content(expectedJson)
                                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                                        .accept(MediaType.APPLICATION_JSON_VALUE))
                                .andExpect(status().isOk())
                                .andDo(print())
                                .andReturn();

                        String actualJson = result.getResponse().getContentAsString();

                        Assertions.assertEquals(expectedJson, actualJson);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Test
    void person404() throws Exception {
        when(personService.getById(anyInt())).thenThrow(new EntityNotFoundException());

        mockMvc.perform(get("/api/v1/persons/{id}", anyInt())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void createPerson400() throws Exception {
        final String wrongSendJson = "{\n" +
                "  \"first_name\": \"Alexander\",\n" +
                "  \"last_name\": \"Urumov\",\n" +
                "  \"age\": 23\n" +
                "}";

        mockMvc.perform(post("/api/v1/persons")
                        .content(wrongSendJson)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(400))
                .andDo(print());
    }

    @Test
    void createPerson201() throws Exception {
        PersonRequest request = new PersonRequest()
                .setAge(25)
                .setFirstName("firstName")
                .setLastName("lastName");

        when(personService.create(request)).thenReturn(1);

        String expectedJson = gson.toJson(request);

        mockMvc.perform(post("/api/v1/persons")
                        .content(expectedJson)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, containsString("/api/v1/persons/1")))
                .andDo(print());
    }

    @Test
    void updatePerson200() {
        personFullResponses.stream()
                .limit(1)
                .forEach(person -> {
                    try {
                        final int id = 1;
                        PersonRequest request = new PersonRequest()
                                .setAge(person.getAge())
                                .setFirstName("newFirstName")
                                .setLastName("newLastName");

                        person.setId(id);
                        person.setFullName("newFirstName newLastName");

                        when(personService.update(id, request)).thenReturn(person);

                        final String sendJson = gson.toJson(request);

                        final String expectedJson = gson.toJson(person);

                        MvcResult result = mockMvc.perform(patch("/api/v1/persons/{id}", id)
                                        .content(sendJson)
                                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                                        .accept(MediaType.APPLICATION_JSON_VALUE))
                                .andExpect(status().isOk())
                                .andDo(print())
                                .andReturn();

                        String actualJson = result.getResponse().getContentAsString();

                        Assertions.assertEquals(expectedJson, actualJson);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Test
    void updatePerson404() throws Exception {
        final int id = 1;

        PersonRequest request = new PersonRequest()
                .setAge(20)
                .setFirstName("newFirstName")
                .setLastName("newLastName");

        when(personService.update(id, request)).thenThrow(new EntityNotFoundException());

        final String sendJson = gson.toJson(request);

        mockMvc.perform(patch("/api/v1/persons/{id}", id)
                        .content(sendJson)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void updatePerson400() throws Exception {
        final String wrongSendJson = "{\n" +
                "  \"first_name\": \"Alexander\",\n" +
                "  \"last_name\": \"Urumov\",\n" +
                "  \"age\": 23\n" +
                "}";

        mockMvc.perform(patch("/api/v1/persons/{id}", 1)
                        .content(wrongSendJson)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(400))
                .andDo(print());
    }

    @Test
    void deletePerson204() throws Exception {
        when(personService.getById(1)).thenReturn(new PersonFullResponse());

        mockMvc.perform(delete("/api/v1/persons/{id}", 1))
                .andExpect(status().is(204))
                .andDo(print());
    }
}