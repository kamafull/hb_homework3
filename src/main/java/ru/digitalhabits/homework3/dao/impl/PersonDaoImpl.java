package ru.digitalhabits.homework3.dao.impl;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Repository;
import ru.digitalhabits.homework3.dao.PersonDao;
import ru.digitalhabits.homework3.domain.Person;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class PersonDaoImpl implements PersonDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Nullable
    @Override
    public Person findById(@Nonnull Integer id) {
        // TODO: NotImplemented
        return entityManager.find(Person.class, id);
    }

    @Nonnull
    @Override
    public List<Person> findAll() {
        // TODO: NotImplemented
        TypedQuery<Person> query = entityManager.createQuery("SELECT p FROM Person p", Person.class);
        return query.getResultList();
    }

    @Nonnull
    @Override
    public Person update(@Nonnull Person person) {
        // TODO: NotImplemented
        if (person.getId() == null) {
            entityManager.persist(person);
        } else {
            entityManager.merge(person);
        }
        return person;
    }

    @Nullable
    @Override
    public Person delete(@Nonnull Integer id) {
        // TODO: NotImplemented
        Person person = findById(id);
        entityManager.remove(person);
        return person;
    }
}
