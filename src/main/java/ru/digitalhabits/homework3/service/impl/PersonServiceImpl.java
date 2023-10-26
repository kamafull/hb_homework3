package ru.digitalhabits.homework3.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.digitalhabits.homework3.dao.DepartmentDao;
import ru.digitalhabits.homework3.dao.PersonDao;
import ru.digitalhabits.homework3.domain.Department;
import ru.digitalhabits.homework3.domain.Person;
import ru.digitalhabits.homework3.model.DepartmentShortResponse;
import ru.digitalhabits.homework3.model.PersonFullResponse;
import ru.digitalhabits.homework3.model.PersonRequest;
import ru.digitalhabits.homework3.model.PersonShortResponse;
import ru.digitalhabits.homework3.service.PersonService;

import javax.annotation.Nonnull;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {
    private final PersonDao personDao;
    private final DepartmentDao departmentDao;

    @Nonnull
    @Override
    @Transactional(readOnly = true)
    public List<PersonShortResponse> findAll() {
        // TODO: NotImplemented: получение информации о всех людях во всех отделах
        return personDao.findAll()
                .stream()
                .map(this::getPersonShortResponse)
                .collect(Collectors.toList());
    }

    @Nonnull
    @Override
    @Transactional(readOnly = true)
    public PersonFullResponse getById(int id) {
        // TODO: NotImplemented: получение информации о человеке. Если не найдено, отдавать 404:NotFound
        Person person = personDao.findById(id);
        if (person == null) throw new EntityNotFoundException();
        return getPersonFullResponse(person);
    }

    @Override
    @Transactional
    public int create(@Nonnull PersonRequest request) {
        // TODO: NotImplemented: создание новой записи о человеке
        Person person = new Person();
        person.setFirstName(request.getFirstName());
        person.setLastName(request.getLastName());
        person.setMiddleName(request.getMiddleName());
        person.setAge(request.getAge());
        personDao.update(person);
        return person.getId();
    }

    @Nonnull
    @Override
    @Transactional
    public PersonFullResponse update(int id, @Nonnull PersonRequest request) {
        // TODO: NotImplemented: обновление информации о человеке. Если не найдено, отдавать 404:NotFound
        Person person = personDao.findById(id);
        if (person == null) throw new EntityNotFoundException();
        person.setFirstName(request.getFirstName());
        person.setLastName(request.getLastName());
        person.setMiddleName(request.getMiddleName());
        person.setAge(request.getAge());
        personDao.update(person);
        return getPersonFullResponse(person);
    }

    @Override
    @Transactional
    public void delete(int id) {
        // TODO: NotImplemented: удаление информации о человеке и удаление его из отдела. Если не найдено, ничего не делать
        personDao.delete(id);
    }


    @Override
    @Transactional
    public void addPersonToDepartment(int departmentId, int personId) {
        // TODO: NotImplemented: добавление нового человека в департамент.
        //  Если не найден человек или департамент, отдавать 404:NotFound.
        //  Если департамент закрыт, то отдавать 409:Conflict
        Department department = departmentDao.findById(departmentId);
        Person person = personDao.findById(personId);
        if (person == null || department == null) throw new EntityNotFoundException();
        if (department.getClosed()) throw new IllegalStateException();
        person.setDepartment(department);
        personDao.update(person);
    }

    @Override
    @Transactional
    public void removePersonFromDepartment(int departmentId, int personId) {
        // TODO: NotImplemented: удаление человека из департамента.
        //  Если департамент не найден, отдавать 404:NotFound, если не найден человек в департаменте, то ничего не делать
        Department department = departmentDao.findById(departmentId);
        Person person = personDao.findById(personId);
        if (department == null) throw new EntityNotFoundException();
        person.setDepartment(null);
    }

    public PersonShortResponse getPersonShortResponse(Person person) {
        return PersonShortResponse.builder()
                .id(person.getId())
                .fullName(person.getFirstName() + " " + person.getLastName())
                .build();
    }

    public PersonFullResponse getPersonFullResponse(Person person) {
        return person != null ? PersonFullResponse.builder()
                .id(person.getId())
                .fullName(person.getFirstName() + " " + person.getLastName())
                .age(person.getAge())
                .department(person.getDepartment() != null ? DepartmentShortResponse.builder()
                        .id(person.getDepartment().getId())
                        .name(person.getDepartment().getName())
                        .build() : null)
                .build() : null;
    }

    @Override
    @Transactional
    public List<PersonShortResponse> getPersonsShortResponseByDepartment(Department department) {
        return personDao.findAll()
                .stream()
                .filter(a -> a.getDepartment() != null)
                .filter(a -> a.getDepartment().equals(department))
                .map(this::getPersonShortResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void updatePerson(Person person) {
        personDao.update(person);
    }

    @Override
    public List<Person> getPersonsByDepartment(Department department) {
        return personDao.findAll()
                .stream()
                .filter(a -> a.getDepartment().equals(department))
                .collect(Collectors.toList());
    }
}

