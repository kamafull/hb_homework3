package ru.digitalhabits.homework3.dao;

import org.springframework.stereotype.Repository;
import ru.digitalhabits.homework3.domain.Person;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class PersonDaoImpl
        implements PersonDao {

    @PersistenceContext
    private EntityManager em;

    @Nullable
    @Override
    public Person findById(@Nonnull Integer id) {
        return em.find(Person.class, id);
    }

    @Nonnull
    @Override
    public List<Person> findAll() {
        return em.createNamedQuery("Person.findAll", Person.class).getResultList();
    }

    @Nonnull
    @Override
    public Person update(@Nonnull Person person) {
        return em.merge(person);
    }

    @Nullable
    @Override
    public Person delete(@Nonnull Integer id) {
        final Person person = this.findById(id);
        em.remove(person);
        return person;
    }
}
