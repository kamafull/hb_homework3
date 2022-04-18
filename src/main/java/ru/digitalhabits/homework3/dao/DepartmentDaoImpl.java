package ru.digitalhabits.homework3.dao;

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
    private EntityManager em;

    @Nullable
    @Override
    public Department findById(@Nonnull Integer id) {
        return em.find(Department.class, id);
    }

    @Nonnull
    @Override
    public List<Department> findAll() {
        return em.createNamedQuery("Department.findAll", Department.class).getResultList();
    }

    @Nonnull
    @Override
    public Department update(@Nonnull Department department) {
        return em.merge(department);
    }

    @Nullable
    @Override
    public Department delete(@Nonnull Integer id) {
        final Department department = this.findById(id);
        em.remove(department);
        return department;
    }
}
