package ru.digitalhabits.homework3.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.digitalhabits.homework3.dao.DepartmentDao;
import ru.digitalhabits.homework3.dao.PersonDao;
import ru.digitalhabits.homework3.domain.Department;
import ru.digitalhabits.homework3.domain.Person;
import ru.digitalhabits.homework3.model.person.PersonFullResponse;
import ru.digitalhabits.homework3.model.person.PersonRequest;
import ru.digitalhabits.homework3.model.person.PersonShortResponse;

import javax.annotation.Nonnull;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl
        implements PersonService {

    private final PersonDao personDao;
    private final DepartmentDao departmentDao;

    @Nonnull
    @Override
    @Transactional(readOnly = true)
    public List<PersonShortResponse> findAll() {
        return Optional.of(
                        personDao.findAll().stream()
                                .map(PersonShortResponse::new)
                                .collect(Collectors.toList()))
                .get();
    }

    @Nonnull
    @Override
    @Transactional(readOnly = true)
    public PersonFullResponse getById(int id) {
        return Optional.ofNullable(personDao.findById(id))
                .map(PersonFullResponse::new)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    @Transactional
    public int create(@Nonnull PersonRequest request) {
        final Person person = new Person()
                .setAge(request.getAge())
                .setFirstName(request.getFirstName())
                .setLastName(request.getLastName())
                .setMiddleName(request.getMiddleName());
        return personDao.update(person).getId();
    }

    @Nonnull
    @Override
    @Transactional
    public PersonFullResponse update(int id, @Nonnull PersonRequest request) {
        return Optional.ofNullable(personDao.findById(id))
                .map(p -> {
                    p.setAge(request.getAge())
                            .setFirstName(request.getFirstName())
                            .setLastName(request.getLastName())
                            .setMiddleName(request.getMiddleName());
                    return new PersonFullResponse(personDao.update(p));
                })
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    @Transactional
    public void delete(int id) {
        Optional.ofNullable(personDao.findById(id))
                .map(person -> personDao.delete(person.getId()));
    }

    @Override
    @Transactional
    public void addPersonToDepartment(int departmentId, int personId) {
        Department department = Optional.ofNullable(departmentDao.findById(departmentId))
                .map(dep -> {
                    if (dep.isClosed())
                        throw new IllegalStateException();
                    return dep;
                })
                .orElseThrow(EntityNotFoundException::new);

        Optional.ofNullable(personDao.findById(personId))
                .map(person -> {
                    person.setDepartment(department);
                    return personDao.update(person);
                })
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    @Transactional
    public void removePersonFromDepartment(int departmentId, int personId) {
        Department department = Optional
                .ofNullable(departmentDao.findById(departmentId))
                .filter(dep -> !dep.isClosed())
                .orElseThrow(EntityNotFoundException::new);

        Optional.ofNullable(personDao.findById(personId))
                .filter(person -> person.getDepartment().equals(department))
                .map(person -> {
                    person.setDepartment(null);
                    return personDao.update(person);
                });
    }
}
