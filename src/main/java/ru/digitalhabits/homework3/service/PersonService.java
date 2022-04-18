package ru.digitalhabits.homework3.service;

import ru.digitalhabits.homework3.model.person.PersonFullResponse;
import ru.digitalhabits.homework3.model.person.PersonRequest;
import ru.digitalhabits.homework3.model.person.PersonShortResponse;

import javax.annotation.Nonnull;
import java.util.List;

public interface PersonService {

    @Nonnull
    List<PersonShortResponse> findAll();

    @Nonnull
    PersonFullResponse getById(int id);

    int create(@Nonnull PersonRequest request);

    @Nonnull
    PersonFullResponse update(int id, @Nonnull PersonRequest request);

    void delete(int id);

    void addPersonToDepartment(int departmentId, int personId);

    void removePersonFromDepartment(int departmentId, int personId);
}
