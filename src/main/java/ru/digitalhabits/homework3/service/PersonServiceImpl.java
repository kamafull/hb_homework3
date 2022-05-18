package ru.digitalhabits.homework3.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.digitalhabits.homework3.model.PersonFullResponse;
import ru.digitalhabits.homework3.model.PersonRequest;
import ru.digitalhabits.homework3.model.PersonShortResponse;

import javax.annotation.Nonnull;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl
        implements PersonService {

    @Nonnull
    @Override
    @Transactional(readOnly = true)
    public List<PersonShortResponse> findAll() {
        // TODO: NotImplemented: получение информации о всех людях во всех отделах
        throw new NotImplementedException();
    }

    @Nonnull
    @Override
    @Transactional(readOnly = true)
    public PersonFullResponse getById(int id) {
        // TODO: NotImplemented: получение информации о человеке. Если не найдено, отдавать 404:NotFound
        throw new NotImplementedException();
    }

    @Override
    @Transactional
    public int create(@Nonnull PersonRequest request) {
        // TODO: NotImplemented: создание новой записи о человеке
        throw new NotImplementedException();
    }

    @Nonnull
    @Override
    @Transactional
    public PersonFullResponse update(int id, @Nonnull PersonRequest request) {
        // TODO: NotImplemented: обновление информации о человеке. Если не найдено, отдавать 404:NotFound
        throw new NotImplementedException();
    }

    @Override
    @Transactional
    public void delete(int id) {
        // TODO: NotImplemented: удаление информации о человеке и удаление его из отдела. Если не найдено, ничего не делать
        throw new NotImplementedException();
    }


    @Override
    @Transactional
    public void addPersonToDepartment(int departmentId, int personId) {
        // TODO: NotImplemented: добавление нового человека в департамент.
        //  Если не найден человек или департамент, отдавать 404:NotFound.
        //  Если департамент закрыт, то отдавать 409:Conflict
        throw new NotImplementedException();
    }

    @Override
    @Transactional
    public void removePersonFromDepartment(int departmentId, int personId) {
        // TODO: NotImplemented: удаление человека из департамента.
        //  Если департамент не найден, отдавать 404:NotFound, если не найден человек в департаменте, то ничего не делать
        throw new NotImplementedException();
    }
}
