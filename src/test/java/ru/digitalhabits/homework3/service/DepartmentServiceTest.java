package ru.digitalhabits.homework3.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.web.server.ResponseStatusException;
import ru.digitalhabits.homework3.dao.DepartmentDao;
import ru.digitalhabits.homework3.dao.PersonDao;
import ru.digitalhabits.homework3.domain.Department;
import ru.digitalhabits.homework3.domain.Person;
import ru.digitalhabits.homework3.model.department.DepartmentFullResponse;
import ru.digitalhabits.homework3.model.department.DepartmentRequest;
import ru.digitalhabits.homework3.model.department.DepartmentShortResponse;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@SpringBootTest
@Import(DepartmentServiceImpl.class)
@AutoConfigureTestDatabase
class DepartmentServiceTest {

    private final static int LIST_SIZE = 5;

    @Autowired
    DepartmentService departmentService;

    @MockBean
    DepartmentDao departmentDao;

    @MockBean
    PersonDao personDao;

    private List<Person> persons;
    private List<Department> departments;


    @Test
    void injectedComponentsAreNotNull() {
        assertThat(departmentService).isNotNull();
        assertThat(departmentDao).isNotNull();
        assertThat(personDao).isNotNull();
    }

    @BeforeEach
    void setUp() {
        persons = new ArrayList<>(LIST_SIZE);
        departments = new ArrayList<>(LIST_SIZE);

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

            persons.add(person);
            departments.add(department);
        }
    }

    @Test
    void findAll() {
        when(departmentDao.findAll()).thenReturn(departments);

        List<DepartmentShortResponse> shortResponses = departmentService.findAll();

        Assertions.assertNotNull(shortResponses);
        Assertions.assertEquals(departments.size(), shortResponses.size());

        List<DepartmentShortResponse> mockShortResponses = departments
                .stream()
                .map(DepartmentShortResponse::new)
                .collect(Collectors.toList());

        Assertions.assertEquals(shortResponses, mockShortResponses);
    }

    @Test
    void findIfEmptyList() {
        when(departmentDao.findAll()).thenReturn(Collections.emptyList());

        Assertions.assertEquals(departmentService.findAll(), Collections.emptyList());
    }

    @Test
    void getById() {
        departments.stream()
                .limit(1)
                .forEach(
                        department -> {
                            when(departmentDao.findById(anyInt())).thenReturn(department);
                            DepartmentFullResponse departmentFullResponse = departmentService.getById(department.getId());
                            Assertions.assertNotNull(departmentFullResponse);
                            Assertions.assertEquals(departmentFullResponse, new DepartmentFullResponse(department));
                        }
                );
    }

    @Test
    void getByIdIfNotExist() {
        when(departmentDao.findById(anyInt())).thenReturn(null);

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> departmentService.getById(anyInt()));
    }

    @Test
    void create() {
        departments.stream()
                .limit(1)
                .forEach(department -> {
                    when(departmentDao.findById(anyInt())).thenReturn(department);
                    when(departmentDao.update(any(Department.class))).thenReturn(department);

                    DepartmentRequest departmentRequest = new DepartmentRequest()
                            .setName(department.getName());

                    int id = departmentService.create(departmentRequest);
                    DepartmentFullResponse departmentFullResponse = departmentService.getById(id);

                    Assertions.assertNotNull(departmentFullResponse);
                    Assertions.assertEquals(id, department.getId());
                    Assertions.assertEquals(departmentFullResponse, new DepartmentFullResponse(department));
                });
    }

    @Test
    void update() {
        departments.stream()
                .limit(1)
                .forEach(department -> {
                    when(departmentDao.findById(anyInt())).thenReturn(department);
                    department.setName("NewNameDepartment");
                    when(departmentDao.update(any(Department.class))).thenReturn(department);

                    DepartmentRequest departmentRequest = new DepartmentRequest()
                            .setName(department.getName());

                    DepartmentFullResponse departmentFullResponse = departmentService.update(department.getId(),
                            departmentRequest);

                    Assertions.assertNotNull(departmentFullResponse);
                    Assertions.assertEquals(departmentFullResponse, new DepartmentFullResponse(department));
                });
    }

    @Test
    void updateIfNotExistDepartment() {
        when(departmentDao.findById(anyInt())).thenReturn(null);

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> departmentService.update(anyInt(), new DepartmentRequest()));
    }

    @Test
    void delete() {
        departments.stream()
                .limit(1)
                .forEach(department -> {
                    persons.forEach(p -> p.setDepartment(department));
                    department.setPersons(persons);

                    when(departmentDao.findById(anyInt())).thenReturn(department).thenReturn(null);

                    department.getPersons().forEach(person -> {
                        person.setDepartment(null);
                        personDao.update(person);
                    });
                    department.setPersons(null);

                    when(departmentDao.delete(anyInt())).thenReturn(department);

                    departmentService.delete(department.getId());
                    verify(departmentDao, times(1)).delete(department.getId());
                    Assertions.assertNull(departmentDao.findById(department.getId()));
                });
    }

    @Test
    void close() {
        departments.stream()
                .limit(1)
                .forEach(department -> {
                    persons.forEach(p -> p.setDepartment(department));
                    department.setPersons(persons);
                    when(departmentDao.findById(anyInt())).thenReturn(department);

                    department.setClosed(true);
                    department.getPersons().forEach(person -> {
                        person.setDepartment(null);
                        personDao.update(person);
                    });
                    department.setPersons(null);

                    when(departmentDao.update(any(Department.class))).thenReturn(department);

                    Assertions.assertTrue(departmentService.getById(anyInt()).isClosed());
                    Assertions.assertNull(departmentService.getById(anyInt()).getPersons());
                });
    }

    @Test
    void closeIfNotExistDepartment() {
        when(departmentDao.findById(anyInt())).thenReturn(null);

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> departmentService.close(anyInt()));
    }
}