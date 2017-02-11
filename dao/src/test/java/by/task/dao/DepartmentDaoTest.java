package by.task.dao;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import by.task.model.Department;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


import static org.junit.Assert.assertEquals;

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

    @Test
    public void testAdd(){
        Department department1 = new Department(0, "testName");

        assertEquals(4, departmentDao.add(department1));
        assertEquals(departmentDao.getById(5), department1);
    }

    @Test
    public void testUpdateGalaxy(){
//        Department department1 = new Department(0, "NAME");
//
//        System.out.println(departmentDao.add(department1));
//        assertEquals(7, departmentDao.add(department1));
    }

}
