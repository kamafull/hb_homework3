package ru.digitalhabits.homework3.dao.impl;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Repository;
import ru.digitalhabits.homework3.dao.DepartmentDao;
import ru.digitalhabits.homework3.domain.Department;
import ru.digitalhabits.homework3.domain.Person;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class DepartmentDaoImpl implements DepartmentDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Nullable
    @Override
    public Department findById(@Nonnull Integer id) {
        // TODO: NotImplemented
        return entityManager.find(Department.class, id);
    }

    @Nonnull
    @Override
    public List<Department> findAll() {
        // TODO: NotImplemented
        TypedQuery<Department> query = entityManager.createQuery("SELECT d FROM Department d", Department.class);
        return query.getResultList();
    }

    @Nonnull
    @Override
    public Department update(@Nonnull Department department) {
        // TODO: NotImplemented
        if (department.getId() == null) {
            entityManager.persist(department);
        } else {
            entityManager.merge(department);
        }
        return department;
    }

    @Nullable
    @Override
    public Department delete(@Nonnull Integer id) {
        // TODO: NotImplemented
        Department department = findById(id);
        entityManager.remove(department);
        return department;
    }
}
