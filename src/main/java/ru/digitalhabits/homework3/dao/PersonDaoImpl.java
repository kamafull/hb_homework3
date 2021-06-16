package ru.digitalhabits.homework3.dao;

import org.apache.commons.lang3.NotImplementedException;
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
    private EntityManager entityManager;

    @Nullable
    @Override
    public Person findById(@Nonnull Integer id) {
        // TODO: NotImplemented
        throw new NotImplementedException();
    }

    @Nonnull
    @Override
    public List<Person> findAll() {
        // TODO: NotImplemented
        throw new NotImplementedException();
    }

    @Nonnull
    @Override
    public Person update(@Nonnull Person person) {
        // TODO: NotImplemented
        throw new NotImplementedException();
    }

    @Nullable
    @Override
    public Person delete(@Nonnull Integer id) {
        // TODO: NotImplemented
        throw new NotImplementedException();
    }
}
