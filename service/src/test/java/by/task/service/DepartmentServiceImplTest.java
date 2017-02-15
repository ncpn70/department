package by.task.service;

import by.task.dao.DepartmentDao;
import by.task.model.Department;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.Assert.*;

/**
 * Created by simpson on 12.2.17.
 */

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring-service-test.xml"})
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
public class DepartmentServiceImplTest {

    @Autowired
    private DepartmentDao departmentDao;

    @Test
    public void add() throws Exception {
        Department department1 = new Department(0, "INSERT_TEST", 100);


        assertEquals(4, departmentDao.add(department1));

        Department department2 = departmentDao.getById(4);
        department2.setDepartmentId(0);
        assertEquals(department2, department1);
    }

    @Test
    public void update() throws Exception {
        Department department = departmentDao.getById(0);
        department.setDepartmentName("UPDATED NAME");
        departmentDao.update(department);

        assertEquals(departmentDao.getById(0).getDepartmentName(),
                "UPDATED NAME");
    }

    @Test
    public void remove() throws Exception {
        long idToRemove = 1;

        departmentDao.remove(idToRemove);

        Throwable ex = catchThrowable(() -> {
            departmentDao.getById(idToRemove);
        });

        assertThat(ex).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    public void getAll() throws Exception {
        assertEquals(departmentDao.getAll().size(), 4);

    }

    @Test
    public void getById() throws Exception {
        assertEquals(departmentDao.getById(1).getDepartmentName(), "Department 2");
    }

}