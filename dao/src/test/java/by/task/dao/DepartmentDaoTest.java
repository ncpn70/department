package by.task.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.runner.RunWith;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import by.task.model.Department;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by simpson on 9.2.17.
 */

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring-dao-test.xml"})
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
public class DepartmentDaoTest {

    @Autowired
    private DepartmentDao departmentDao;

    private static Logger LOGGER = LogManager.getLogger(DepartmentDaoTest.class);

    @Test
    public void testAdd(){
        Department department1 = new Department(0, "INSERT_TEST");

        assertEquals(4, departmentDao.add(department1));

        Department department2 = departmentDao.getById(4);
        department2.setDepartmentId(0);
        assertEquals(department2, department1);
    }

    @Test
    public void testUpdate(){
        Department department = departmentDao.getById(0);
        department.setDepartmentName("UPDATED NAME");
        departmentDao.update(department);

        assertEquals(departmentDao.getById(0).getDepartmentName(),
                "UPDATED NAME");
    }

    @Test
    public void testDelete(){
        long idToRemove = 1;

        departmentDao.remove(idToRemove);

        Throwable ex = catchThrowable(() -> {
            departmentDao.getById(idToRemove);
        });

        assertThat(ex).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    public void testGetAll(){
        assertEquals(departmentDao.getAll().size(), 4);
    }

    @Test
    public void testGetById(){
        assertEquals(departmentDao.getById(1).getDepartmentName(), "Department 2");
    }

}
