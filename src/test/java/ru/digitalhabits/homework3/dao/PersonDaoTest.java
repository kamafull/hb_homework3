package ru.digitalhabits.homework3.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import ru.digitalhabits.homework3.domain.Person;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@Import(PersonDaoImpl.class)
class PersonDaoTest {

    private final static int PERSONS_LIST_SIZE = 5;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PersonDao personDao;

    private List<Person> persons;

    @Test
    void injectedComponentsAreNotNull() {
        assertThat(entityManager).isNotNull();
        assertThat(personDao).isNotNull();
    }

    @BeforeEach
    public void setUp() {
        persons = new ArrayList<>(PERSONS_LIST_SIZE);

        for (int i = 1; i < PERSONS_LIST_SIZE + 1; i++) {
            persons.add(new Person()
                    .setFirstName("firstName" + i)
                    .setLastName("lastName" + i)
                    .setAge(25 + i)
            );
        }
    }

    @Test
    void findById() {
        persons.stream()
                .limit(1)
                .forEach(person -> {
                    entityManager.persist(person);
                    Assertions.assertEquals(personDao.findById(person.getId()), person);
                });
    }

    @Test
    void findAll() {
        persons.forEach(person -> entityManager.persist(person));
        Assertions.assertEquals(personDao.findAll(), persons);
    }

    @Test
    void update() {
        persons.stream()
                .limit(1)
                .forEach(person -> {
                    entityManager.persist(person);
                    person.setLastName("NewLastName");
                    Assertions.assertEquals(personDao.update(person), person);
                });
    }

    @Test
    void delete() {
        persons.stream()
                .limit(1)
                .forEach(person -> {
                    entityManager.persist(person);
                    Assertions.assertEquals(personDao.delete(person.getId()), person);
                });
    }
}