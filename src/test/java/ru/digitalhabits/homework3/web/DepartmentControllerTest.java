package ru.digitalhabits.homework3.web;

import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.digitalhabits.homework3.domain.Department;
import ru.digitalhabits.homework3.domain.Person;
import ru.digitalhabits.homework3.model.department.DepartmentFullResponse;
import ru.digitalhabits.homework3.model.department.DepartmentRequest;
import ru.digitalhabits.homework3.model.department.DepartmentShortResponse;
import ru.digitalhabits.homework3.service.DepartmentService;
import ru.digitalhabits.homework3.service.DepartmentServiceImpl;
import ru.digitalhabits.homework3.service.PersonService;
import ru.digitalhabits.homework3.service.PersonServiceImpl;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = DepartmentController.class)
@Import({DepartmentServiceImpl.class,
        PersonServiceImpl.class})
class DepartmentControllerTest {

    private final static int LIST_SIZE = 5;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    DepartmentService departmentService;

    @MockBean
    PersonService personService;


    private List<DepartmentShortResponse> departmentShortResponses;
    private List<DepartmentFullResponse> departmentFullResponses;

    @Test
    void injectedComponentsAreNotNull() {
        assertThat(mockMvc).isNotNull();
        assertThat(departmentService).isNotNull();
    }

    @BeforeEach
    void setUp() {
        departmentShortResponses = new ArrayList<>(LIST_SIZE);
        departmentFullResponses = new ArrayList<>(LIST_SIZE);

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

            departmentShortResponses.add(new DepartmentShortResponse(department));
            departmentFullResponses.add(new DepartmentFullResponse(department));
        }
    }

    @Test
    void departments200() throws Exception {
        when(departmentService.findAll()).thenReturn(departmentShortResponses);

        final String expectedJson = gson.toJson(departmentShortResponses);

        MvcResult result = mockMvc.perform(get("/api/v1/departments")
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
    void departmentsIfEmpty200() throws Exception {
        when(personService.findAll()).thenReturn(Collections.emptyList());

        final String expectedJson = gson.toJson(Collections.emptyList());

        MvcResult result = mockMvc.perform(get("/api/v1/departments")
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
    void department200() {
        departmentFullResponses.stream()
                .limit(1)
                .forEach(department -> {
                    try {
                        when(departmentService.getById(anyInt())).thenReturn(department);

                        final String expectedJson = gson.toJson(department);

                        MvcResult result = mockMvc.perform(get("/api/v1/departments/{id}", anyInt())
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
    void department404() throws Exception {
        when(departmentService.getById(anyInt())).thenThrow(new EntityNotFoundException());

        mockMvc.perform(get("/api/v1/departments/{id}", anyInt())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void createDepartment201() throws Exception {
        DepartmentRequest request = new DepartmentRequest()
                .setName("Name");

        when(departmentService.create(request)).thenReturn(1);

        String expectedJson = gson.toJson(request);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/departments")
                        .content(expectedJson)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, containsString("/api/v1/departments/1")))
                .andDo(print());
    }

    @Test
    void createDepartment400() throws Exception {
        final String wrongSendJson = "{\n" +
                "  \"name_department\": \"NameDepartment\"\n" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/departments")
                        .content(wrongSendJson)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(400))
                .andDo(print());
    }

    @Test
    void updateDepartment200() {
        departmentFullResponses.stream()
                .limit(1)
                .forEach(department -> {
                    try {
                        final int id = 1;
                        DepartmentRequest request = new DepartmentRequest()
                                .setName("NewNameDepartment");

                        department.setId(id);
                        department.setName("NewNameDepartment");

                        when(departmentService.update(id, request)).thenReturn(department);

                        final String sendJson = gson.toJson(request);

                        final String expectedJson = gson.toJson(department);

                        MvcResult result = mockMvc.perform(patch("/api/v1/departments/{id}", id)
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
    void updateDepartment404() throws Exception {
        final int id = 1;
        DepartmentRequest request = new DepartmentRequest()
                .setName("NewNameDepartment");

        when(departmentService.update(id, request)).thenThrow(new EntityNotFoundException());

        final String sendJson = gson.toJson(request);

        mockMvc.perform(patch("/api/v1/departments/{id}", id)
                        .content(sendJson)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void updateDepartment400() throws Exception {
        final String wrongSendJson = "{\n" +
                "  \"name_department\": \"NameDepartment\"\n" +
                "}";

        mockMvc.perform(patch("/api/v1/departments/{id}", 1)
                        .content(wrongSendJson)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(400))
                .andDo(print());
    }

    @Test
    void deleteDepartment204() throws Exception {
        when(departmentService.getById(1)).thenReturn(any(DepartmentFullResponse.class));

        mockMvc.perform(delete("/api/v1/departments/{id}", 1))
                .andExpect(status().is(204))
                .andDo(print());
    }

    @Test
    void addPersonToDepartment204() throws Exception {
        mockMvc.perform(post("/api/v1/departments/{departmentId}/{personId}", 1, 1))
                .andExpect(status().is(204))
                .andDo(print());
    }

    @Test
    void addPersonToDepartment404() throws Exception {
        doThrow(new EntityNotFoundException()).when(personService).addPersonToDepartment(anyInt(), anyInt());

        mockMvc.perform(post("/api/v1/departments/{departmentId}/{personId}", 1, 1))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void addPersonToDepartment409() throws Exception {
        doThrow(new IllegalStateException()).when(personService).addPersonToDepartment(anyInt(), anyInt());

        mockMvc.perform(post("/api/v1/departments/{departmentId}/{personId}", 1, 1))
                .andExpect(status().isConflict())
                .andDo(print());
    }

    @Test
    void removePersonToDepartment204() throws Exception {
        mockMvc.perform(delete("/api/v1/departments/{departmentId}/{personId}", 1, 1))
                .andExpect(status().is(204))
                .andDo(print());
    }

    @Test
    void removePersonToDepartment404() throws Exception {
        doThrow(new EntityNotFoundException()).when(personService).removePersonFromDepartment(anyInt(), anyInt());

        mockMvc.perform(delete("/api/v1/departments/{departmentId}/{personId}", 1, 1))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void closeDepartment204() throws Exception {
        mockMvc.perform(post("/api/v1/departments/{id}/close", 1))
                .andExpect(status().is(204))
                .andDo(print());
    }
}