package ru.digitalhabits.homework3.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.digitalhabits.homework3.domain.Department;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class DepartmentDaoTest {
    @Autowired
    private DepartmentDao departmentDao;

    @Test
    void findById() {
        // TODO: NotImplemented
        Department department = departmentDao.findById(1);
        assertThat(department).isNotNull();
    }

    @Test
    void findAll() {
        // TODO: NotImplemented
        List<Department> departmentList = departmentDao.findAll();
        assertThat(departmentList).isNotNull();
    }

    @Test
    @Transactional
    void update() {
        // TODO: NotImplemented
        Department department = departmentDao.findById(1);
        department.setName("ALLA");
        departmentDao.update(department);
        department = departmentDao.findById(1);
        assertThat(department.getName()).isEqualTo("ALLA");
    }

    @Test
    @Transactional
    void delete() {
        // TODO: NotImplemented
        departmentDao.delete(1);
        Department department = departmentDao.findById(1);
        assertThat(department).isNull();
    }
}