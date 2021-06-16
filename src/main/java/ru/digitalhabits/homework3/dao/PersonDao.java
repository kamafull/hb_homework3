package ru.digitalhabits.homework3.dao;

import org.springframework.data.repository.NoRepositoryBean;
import ru.digitalhabits.homework3.domain.Person;

@NoRepositoryBean
public interface PersonDao
        extends CrudOperations<Person, Integer> {}