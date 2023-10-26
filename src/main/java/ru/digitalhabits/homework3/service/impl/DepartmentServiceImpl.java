package ru.digitalhabits.homework3.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.digitalhabits.homework3.dao.DepartmentDao;
import ru.digitalhabits.homework3.domain.Department;
import ru.digitalhabits.homework3.domain.Person;
import ru.digitalhabits.homework3.model.DepartmentFullResponse;
import ru.digitalhabits.homework3.model.DepartmentRequest;
import ru.digitalhabits.homework3.model.DepartmentShortResponse;
import ru.digitalhabits.homework3.service.DepartmentService;
import ru.digitalhabits.homework3.service.PersonService;

import javax.annotation.Nonnull;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentDao departmentDao;
    private final PersonService personService;

    @Nonnull
    @Override
    @Transactional(readOnly = true)
    public List<DepartmentShortResponse> findAll() {
        // TODO: NotImplemented: получение краткой информации о всех департаментах
        return departmentDao.findAll()
                .stream()
                .map(this::getDepartmentShortResponse)
                .collect(Collectors.toList());
    }

    @Nonnull
    @Override
    @Transactional(readOnly = true)
    public DepartmentFullResponse getById(int id) {
        // TODO: NotImplemented: получение подробной информации о департаменте и краткой информации о людях в нем.
        //  Если не найдено, отдавать 404:NotFound
        Department department = departmentDao.findById(id);
        if (department == null) throw new EntityNotFoundException();
        return getDepartmentFullResponse(department);
    }

    @Override
    @Transactional
    public int create(@Nonnull DepartmentRequest request) {
        // TODO: NotImplemented: создание нового департамента
        Department department = new Department();
        department.setName(request.getName());
        departmentDao.update(department);
        return department.getId();
    }

    @Nonnull
    @Override
    @Transactional
    public DepartmentFullResponse update(int id, @Nonnull DepartmentRequest request) {
        // TODO: NotImplemented: обновление данных о департаменте. Если не найдено, отдавать 404:NotFound
        Department department = departmentDao.findById(id);
        if (department == null) throw new EntityNotFoundException();
        department.setName(request.getName());
        departmentDao.update(department);
        return getDepartmentFullResponse(department);
    }

    @Override
    @Transactional
    public void delete(int id) {
        // TODO: NotImplemented: удаление всех людей из департамента и удаление самого департамента.
        //  Если не найдено, то ничего не делать
        departmentDao.delete(id);
    }

    @Override
    @Transactional
    public void close(int id) {
        // TODO: NotImplemented: удаление всех людей из департамента и установка отметки на департаменте,
        //  что он закрыт для добавления новых людей. Если не найдено, отдавать 404:NotFound
        Department department = departmentDao.findById(id);
        if(department == null) throw new EntityNotFoundException();
        department.setClosed(true);
        departmentDao.update(department);
        for (Person person : personService.getPersonsByDepartment(department)) {
            person.setDepartment(null);
            personService.updatePerson(person);
        }
    }

    public DepartmentShortResponse getDepartmentShortResponse(Department department) {
        return DepartmentShortResponse.builder()
                .id(department.getId())
                .name(department.getName())
                .build();
    }

    public DepartmentFullResponse getDepartmentFullResponse(Department department) {
        if (department == null) {
            return null;
        } else {
            return DepartmentFullResponse.builder()
                    .id(department.getId())
                    .name(department.getName())
                    .closed(department.getClosed())
                    .persons(personService.getPersonsShortResponseByDepartment(department))
                    .build();
        }
    }
}
