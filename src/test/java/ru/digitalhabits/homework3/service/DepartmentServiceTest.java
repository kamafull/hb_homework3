package ru.digitalhabits.homework3.service;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import ru.digitalhabits.homework3.dao.DepartmentDao;
import ru.digitalhabits.homework3.domain.Department;
import ru.digitalhabits.homework3.model.DepartmentRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class DepartmentServiceTest {
    @Autowired
    DepartmentService departmentService;
    @Autowired
    DepartmentDao departmentDao;

    @Test
    void findAll() {
        // TODO: NotImplemented
        List<?> departmentList = departmentService.findAll();
        assertThat(departmentList).isNotNull();
    }

    @Test
    void findById() {
        // TODO: NotImplemented
        Object department = departmentService.getById(1);
        assertThat(department).isNotNull();
    }

    @Test
    @Transactional
    @Rollback
    void create() {
        // TODO: NotImplemented
        DepartmentRequest departmentRequest = new DepartmentRequest();
        departmentRequest.setName("ALLA");
        assertThat(departmentService.create(departmentRequest)).isNotNull();
    }

    @Test
    @Transactional
    @Rollback
    void update() {
        // TODO: NotImplemented
        DepartmentRequest departmentRequest = new DepartmentRequest();
        departmentRequest.setName("ALLA");
        assertThat(departmentService.update(1, departmentRequest)).isNotNull();
    }

    @Test
    @Transactional
    @Rollback
    void delete() {
        // TODO: NotImplemented
        departmentService.delete(1);
        assertThat(departmentDao.findById(1)).isNull();
    }

    @Test
    @Transactional
    @Rollback
    void close() {
        // TODO: NotImplemented
        departmentService.close(1);
        Department department = departmentDao.findById(1);
        assert department != null;
        assertThat(department.getClosed()).isTrue();
    }
}