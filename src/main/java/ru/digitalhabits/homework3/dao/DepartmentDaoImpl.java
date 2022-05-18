package ru.digitalhabits.homework3.dao;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Repository;
import ru.digitalhabits.homework3.domain.Department;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class DepartmentDaoImpl
        implements DepartmentDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Nullable
    @Override
    public Department findById(@Nonnull Integer id) {
        // TODO: NotImplemented
        throw new NotImplementedException();
    }

    @Nonnull
    @Override
    public List<Department> findAll() {
        // TODO: NotImplemented
        throw new NotImplementedException();
    }

    @Nonnull
    @Override
    public Department update(@Nonnull Department department) {
        // TODO: NotImplemented
        throw new NotImplementedException();
    }

    @Nullable
    @Override
    public Department delete(@Nonnull Integer id) {
        // TODO: NotImplemented
        throw new NotImplementedException();
    }
}
