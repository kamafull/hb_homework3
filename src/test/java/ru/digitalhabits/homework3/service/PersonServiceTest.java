package ru.digitalhabits.homework3.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import ru.digitalhabits.homework3.dao.DepartmentDao;
import ru.digitalhabits.homework3.dao.PersonDao;
import ru.digitalhabits.homework3.domain.Department;
import ru.digitalhabits.homework3.model.DepartmentRequest;
import ru.digitalhabits.homework3.model.PersonRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PersonServiceTest {
    @Autowired
    PersonService personService;
    @Autowired
    PersonDao personDao;
    @Autowired
    DepartmentDao departmentDao;

    @Test
    void findAll() {
        // TODO: NotImplemented
        List<?> personList = personService.findAll();
        assertThat(personList).isNotEmpty();
    }

    @Test
    void findById() {
        // TODO: NotImplemented
        Object person = personService.getById(3);
        assertThat(person).isNotNull();
    }

    @Test
    @Rollback()
    @Transactional
    void create() {
        // TODO: NotImplemented
        PersonRequest personRequest = new PersonRequest();
        personRequest.setFirstName("ALLA");
        personRequest.setLastName("PUGACHEVA");
        assertThat(personService.create(personRequest)).isNotNull();
    }

    @Test
    @Rollback
    @Transactional
    void update() {
        // TODO: NotImplemented
        PersonRequest personRequest = new PersonRequest();
        personRequest.setFirstName("ALLA");
        personRequest.setLastName("PUGACHEVA");
        assertThat(personService.update(3, personRequest)).isNotNull();
    }

    @Test
    @Rollback()
    @Transactional
    void delete() {
        // TODO: NotImplemented
        personService.delete(3);
        assertThat(personDao.findById(3)).isNull();
    }

    @Test
    @Rollback()
    @Transactional
    void addPersonToDepartment() {
        // TODO: NotImplemented
        personService.addPersonToDepartment(6,3);
        Department department = departmentDao.findById(1);
        department.setClosed(false);
        departmentDao.update(department);
        assertThat(personDao.findById(3).getDepartment().getId().equals(6)).isTrue();

    }

    @Test
    @Rollback()
    @Transactional
    void removePersonFromDepartment() {
        // TODO: NotImplemented
        personService.removePersonFromDepartment(1, 3);
        assertThat(personDao.findById(3).getDepartment()).isNull();
    }
}