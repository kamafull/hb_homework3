package ru.digitalhabits.homework3.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.digitalhabits.homework3.domain.Person;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PersonDaoTest {
    @Autowired
    private PersonDao personDao;

    @Test
    void findById() {
        // TODO: NotImplemented
        Person person = personDao.findById(1);
        assertThat(person).isNotNull();
    }

    @Test
    void findAll() {
        // TODO: NotImplemented
        List<Person> personList = personDao.findAll();
        assertThat(personList).isNotNull();
    }

    @Test
    @Transactional
    void update() {
        // TODO: NotImplemented
        Person person = personDao.findById(1);
        person.setFirstName("ALLA");
        personDao.update(person);
        person = personDao.findById(1);
        assertThat(person.getFirstName()).isEqualTo("ALLA");
    }

    @Test
    @Transactional
    void delete() {
        // TODO: NotImplemented
        personDao.delete(1);
        Person person = personDao.findById(1);
        assertThat(person).isNull();
    }
}