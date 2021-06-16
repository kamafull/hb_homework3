package ru.digitalhabits.homework3.dao;

import org.springframework.data.repository.NoRepositoryBean;
import ru.digitalhabits.homework3.domain.Department;

@NoRepositoryBean
public interface DepartmentDao
        extends CrudOperations<Department, Integer> {}
