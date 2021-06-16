package ru.digitalhabits.homework3.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
class PersonDaoTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PersonDao personDao;

    @Test
    void findById() {
        // TODO: NotImplemented
    }

    @Test
    void findAll() {
        // TODO: NotImplemented
    }

    @Test
    void update() {
        // TODO: NotImplemented
    }

    @Test
    void delete() {
        // TODO: NotImplemented
    }
}