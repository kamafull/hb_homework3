package ru.digitalhabits.homework3.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.digitalhabits.homework3.dao.DepartmentDao;
import ru.digitalhabits.homework3.dao.PersonDao;
import ru.digitalhabits.homework3.domain.Department;
import ru.digitalhabits.homework3.model.department.DepartmentFullResponse;
import ru.digitalhabits.homework3.model.department.DepartmentRequest;
import ru.digitalhabits.homework3.model.department.DepartmentShortResponse;

import javax.annotation.Nonnull;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl
        implements DepartmentService {

    private final PersonDao personDao;
    private final DepartmentDao departmentDao;

    @Nonnull
    @Override
    @Transactional(readOnly = true)
    public List<DepartmentShortResponse> findAll() {
        return Optional.of(
                        departmentDao.findAll().stream()
                                .map(DepartmentShortResponse::new)
                                .collect(Collectors.toList()))
                .get();
    }

    @Nonnull
    @Override
    @Transactional(readOnly = true)
    public DepartmentFullResponse getById(int id) {
        return Optional.ofNullable(departmentDao.findById(id))
                .map(DepartmentFullResponse::new)
                .orElseThrow(EntityNotFoundException::new);
    }


    @Override
    @Transactional
    public int create(@Nonnull DepartmentRequest request) {
        final Department department = new Department()
                .setName(request.getName());
        return departmentDao.update(department).getId();
    }

    @Nonnull
    @Override
    @Transactional
    public DepartmentFullResponse update(int id, @Nonnull DepartmentRequest request) {
        return Optional.ofNullable(departmentDao.findById(id))
                .map(department -> {
                    department.setName(request.getName());
                    return new DepartmentFullResponse(department);
                })
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    @Transactional
    public void delete(int id) {
        Optional.ofNullable(departmentDao.findById(id))
                .map(department -> {
                    Optional.ofNullable(department.getPersons())
                            .map(persons -> {
                                persons.forEach(person -> {
                                    person.setDepartment(null);
                                    personDao.update(person);
                                });
                                return persons;
                            });
                    department.setPersons(null);
                    departmentDao.delete(id);
                    return Optional.empty();
                });
    }

    @Override
    @Transactional
    public void close(int id) {
        Optional.ofNullable(departmentDao.findById(id))
                .map(department -> {
                    Optional.ofNullable(department.getPersons())
                            .map(persons -> {
                                persons.forEach(person -> {
                                    person.setDepartment(null);
                                    personDao.update(person);
                                });
                                return persons;
                            });
                    department.setClosed(true);
                    department.setPersons(null);
                    return departmentDao.update(department);
                })
                .orElseThrow(EntityNotFoundException::new);
    }
}
