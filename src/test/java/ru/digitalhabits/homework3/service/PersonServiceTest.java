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
import ru.digitalhabits.homework3.model.person.PersonFullResponse;
import ru.digitalhabits.homework3.model.person.PersonRequest;
import ru.digitalhabits.homework3.model.person.PersonShortResponse;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;


@SpringBootTest
@Import(PersonServiceImpl.class)
@AutoConfigureTestDatabase
class PersonServiceTest {

    private final static int LIST_SIZE = 5;

    @Autowired
    PersonService personService;

    @MockBean
    DepartmentDao departmentDao;

    @MockBean
    PersonDao personDao;

    private List<Person> persons;
    private List<Department> departments;


    @Test
    void injectedComponentsAreNotNull() {
        assertThat(personService).isNotNull();
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
        when(personDao.findAll()).thenReturn(persons);

        List<PersonShortResponse> shortResponses = personService.findAll();


        Assertions.assertNotNull(shortResponses);
        Assertions.assertEquals(persons.size(), shortResponses.size());

        List<PersonShortResponse> mockShortResponses = persons
                .stream()
                .map(PersonShortResponse::new)
                .collect(Collectors.toList());

        Assertions.assertEquals(shortResponses, mockShortResponses);
    }

    @Test
    void getById() {
        persons.stream()
                .limit(1)
                .forEach(
                        person -> {
                            when(personDao.findById(anyInt())).thenReturn(person);
                            PersonFullResponse personFullResponse = personService.getById(person.getId());
                            Assertions.assertNotNull(personFullResponse);
                            Assertions.assertEquals(personFullResponse, new PersonFullResponse(person));
                        }
                );
    }

    @Test
    void getByIdIfNotExist() {
        when(personDao.findById(anyInt())).thenReturn(null);

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> personService.getById(anyInt()));
    }

    @Test
    void create() {
        persons.stream()
                .limit(1)
                .forEach(person -> {
                    when(personDao.findById(anyInt())).thenReturn(person);
                    when(personDao.update(any(Person.class))).thenReturn(person);

                    PersonRequest personRequest = new PersonRequest()
                            .setAge(person.getAge())
                            .setFirstName(person.getFirstName())
                            .setLastName(person.getLastName());

                    int id = personService.create(personRequest);
                    PersonFullResponse personFullResponse = personService.getById(id);

                    Assertions.assertNotNull(personFullResponse);
                    Assertions.assertEquals(id, person.getId());
                    Assertions.assertEquals(personFullResponse, new PersonFullResponse(person));
                });
    }

    @Test
    void update() {
        persons.stream()
                .limit(1)
                .forEach(person -> {
                    when(personDao.findById(anyInt())).thenReturn(person);
                    person.setLastName("NewLastName");
                    when(personDao.update(any(Person.class))).thenReturn(person);

                    PersonRequest personRequest = new PersonRequest()
                            .setAge(person.getAge())
                            .setFirstName(person.getFirstName())
                            .setLastName(person.getLastName());

                    PersonFullResponse personFullResponse = personService.update(person.getId(), personRequest);

                    Assertions.assertNotNull(personFullResponse);
                    Assertions.assertEquals(personFullResponse, new PersonFullResponse(person));
                });
    }


    @Test
    void updateIfNotExist() {
        persons.stream()
                .limit(1)
                .forEach(person -> {
                    when(personDao.findById(anyInt())).thenReturn(null);

                    PersonRequest personRequest = new PersonRequest()
                            .setAge(person.getAge())
                            .setFirstName(person.getFirstName())
                            .setLastName(person.getLastName());

                    Assertions.assertThrows(EntityNotFoundException.class,
                            () -> personService.update(person.getId(), personRequest));
                });
    }

    @Test
    void delete() {
        persons.stream()
                .limit(1)
                .forEach(person -> {
                    when(personDao.findById(anyInt())).thenReturn(person).thenReturn(null);
                    when(personDao.delete(anyInt())).thenReturn(person);

                    personService.delete(person.getId());
                    verify(personDao, times(1)).delete(person.getId());
                    Assertions.assertNull(personDao.findById(person.getId()));
                });
    }

    @Test
    void addPersonToDepartment() {
        persons.stream()
                .limit(1)
                .forEach(person -> {
                    when(personDao.findById(anyInt())).thenReturn(person);

                    Department department = departments.stream().findAny().get();
                    when(departmentDao.findById(anyInt())).thenReturn(department);

                    person.setDepartment(department);
                    when(personDao.update(person)).thenReturn(person);

                    personService.addPersonToDepartment(department.getId(), person.getId());

                    Assertions.assertEquals(department, person.getDepartment());
                    Assertions.assertEquals(department.getId(),
                            personService.getById(person.getId()).getDepartment().getId());
                });
    }

    @Test
    void addPersonToDepartmentWhenDepartmentNotExist() {
        when(personDao.findById(anyInt())).thenReturn(null);

        Department department = departments.stream().findAny().get();

        when(departmentDao.findById(anyInt())).thenReturn(department);

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> personService.addPersonToDepartment(department.getId(), anyInt()));
    }

    @Test
    void addPersonToDepartmentWhenPersonNotExist() {
        persons.stream()
                .limit(1)
                .forEach(person -> {
                    when(personDao.findById(anyInt())).thenReturn(null);

                    when(departmentDao.findById(anyInt())).thenReturn(null);

                    when(personDao.update(person)).thenReturn(person);


                    Assertions.assertThrows(EntityNotFoundException.class,
                            () -> personService.addPersonToDepartment(anyInt(), person.getId()));

                });
    }

    @Test
    void addPersonToClosedDepartment() {
        persons.stream()
                .limit(1)
                .forEach(person -> {
                    when(personDao.findById(anyInt())).thenReturn(person);

                    Department department = departments.stream().findAny().get();
                    department.setClosed(true);
                    when(departmentDao.findById(anyInt())).thenReturn(department);

                    when(personDao.update(person)).thenReturn(person);

                    Assertions.assertThrows(IllegalStateException.class,
                            () -> personService.addPersonToDepartment(department.getId(), person.getId()));
                });
    }

    @Test
    void removePersonFromDepartment() {
        persons.stream()
                .limit(1)
                .forEach(person -> {
                    when(personDao.findById(anyInt())).thenReturn(person);

                    Department department = departments.stream().findAny().get();
                    when(departmentDao.findById(anyInt())).thenReturn(department);

                    person.setDepartment(null);
                    when(personDao.update(person)).thenReturn(person);

                    Assertions.assertEquals(person, personDao.findById(person.getId()));
                    Assertions.assertNull(personDao.findById(person.getId()).getDepartment());
                });

    }

    @Test
    void removePersonFromNonExistDepartment() {
        persons.stream()
                .limit(1)
                .forEach(person -> {
                    when(personDao.findById(anyInt())).thenReturn(person);

                    when(departmentDao.findById(anyInt())).thenReturn(null);

                    when(personDao.update(person)).thenReturn(person);

                    Assertions.assertThrows(EntityNotFoundException.class,
                            () -> personService.addPersonToDepartment(anyInt(), person.getId()));

                });

    }
}