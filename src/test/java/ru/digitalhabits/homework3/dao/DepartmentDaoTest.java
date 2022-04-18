package ru.digitalhabits.homework3.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import ru.digitalhabits.homework3.domain.Department;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(DepartmentDaoImpl.class)
class DepartmentDaoTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private DepartmentDao departmentDao;

    private final static int DEPARTMENT_LIST_SIZE = 3;

    private List<Department> departments;

    @Test
    void injectedComponentsAreNotNull() {
        assertThat(entityManager).isNotNull();
        assertThat(departmentDao).isNotNull();
    }

    @BeforeEach
    public void setUp() {
        departments = new ArrayList<>(DEPARTMENT_LIST_SIZE);

        for (int i = 1; i < DEPARTMENT_LIST_SIZE + 1; i++) {
            departments.add(new Department()
                    .setName("NameDepartment" + i)
            );
        }
    }

    @Test
    void findById() {
        departments.stream()
                .limit(1)
                .forEach(department -> {
                    entityManager.persist(department);
                    Assertions.assertEquals(departmentDao.findById(department.getId()), department);
                });
    }

    @Test
    void findAll() {
        departments.forEach(department -> entityManager.persist(department));
        Assertions.assertEquals(departmentDao.findAll(), departments);
    }

    @Test
    void update() {
        departments.stream()
                .limit(1)
                .forEach(department -> {
                    entityManager.persist(department);
                    department.setName("NewNameDepartment");
                    Assertions.assertEquals(departmentDao.update(department), department);
                });
    }

    @Test
    void delete() {
        departments.stream()
                .limit(1)
                .forEach(department -> {
                    entityManager.persist(department);
                    Assertions.assertEquals(departmentDao.delete(department.getId()), department);
                });
    }
}