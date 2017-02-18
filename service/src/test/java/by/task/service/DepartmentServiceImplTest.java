package by.task.service;

import by.task.dao.DepartmentDao;
import by.task.model.Department;
import by.task.service.exception.BadParamException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

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
    private DepartmentService departmentDao;

    @Test
    public void add() throws Exception {
        Department department1 = new Department( "INSERT_TEST", 100);

        assertEquals(4, departmentDao.add(department1));

        Throwable ex = catchThrowable(() -> {
            departmentDao.add(null);
        });

        assertThat(ex).isInstanceOf(BadParamException.class);

        Throwable ex2 = catchThrowable(() -> {
            departmentDao.add(new Department( null, 0));
        });

        assertThat(ex2).isInstanceOf(BadParamException.class);
        assertThat(ex2.getMessage()).isNotBlank();

        Department department2 = departmentDao.getById(4);
        department2.setDepartmentId(0);
        assertEquals(department2, department1);
    }

    @Test
    public void update() throws Exception {
        Department department = departmentDao.getById(0);
        department.setDepartmentName("UPDATED NAME");
        departmentDao.update(department);

        Throwable ex1 = catchThrowable(() -> {
            departmentDao.update(new Department( -3L, "INSERT_TEST", 100));
        });

        assertThat(ex1).isInstanceOf(BadParamException.class);
        assertThat(ex1.getMessage()).isNotBlank();

        Throwable ex = catchThrowable(() -> {
            departmentDao.update(null);
        });

        assertThat(ex).isInstanceOf(BadParamException.class);
        assertThat(ex.getMessage()).isNotBlank();

        Throwable ex2 = catchThrowable(() -> {
            departmentDao.update(new Department( null, 0));
        });

        assertThat(ex2).isInstanceOf(BadParamException.class);
        assertThat(ex2.getMessage()).isNotBlank();

        assertEquals(departmentDao.getById(0).getDepartmentName(),
                "UPDATED NAME");
    }

    @Test
    public void remove() throws Exception {
        long idToRemove = 1;

        departmentDao.remove(idToRemove);

        Throwable ex2 = catchThrowable(() -> {
            departmentDao.remove(-1L);
        });

        assertThat(ex2).isInstanceOf(BadParamException.class);
        assertThat(ex2.getMessage()).isNotBlank();

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
        Throwable ex2 = catchThrowable(() -> {
            departmentDao.getById(-1L);
        });

        assertThat(ex2).isInstanceOf(BadParamException.class);
        assertThat(ex2.getMessage()).isNotBlank();

        assertEquals(departmentDao.getById(1).getDepartmentName(), "Department 2");
    }

}