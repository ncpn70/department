package by.task.dao;

import by.task.model.Employee;
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

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring-dao-test.xml"})
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
public class EmployeeDaoImplTest {

    @Autowired
    private EmployeeDao employeeDao;

    @Test
    public void testAdd(){
        Employee employee1 = new Employee(0, "EMPLOYEE TEST", LocalDate.now(), 100, 2);

        assertEquals(5, employeeDao.add(employee1));

        Employee employee2 = employeeDao.getById(5);
        employee2.setEmployeeId(0);
        assertEquals(employee1, employee2);
    }

    @Test
    public void testUpdate(){
        Employee employee1 = employeeDao.getById(1);
        employee1.setSalary(10004);
        employee1.setDepartmentId(5);
        employee1.setFullName("VIK");

        employeeDao.update(employee1);
        Employee employee2 = employeeDao.getById(1);


        assertEquals(employee1, employee2);
    }

    @Test
    public void testRemove(){
        long id = 1;

        employeeDao.remove(id);

        Throwable ex = catchThrowable(() -> {
            employeeDao.getById(id);
        });

        assertThat(ex).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    public void testGetAll(){
        assertEquals(employeeDao.getAll().size(), 5);
    }

    @Test
    public void testGetById(){
        Employee employee = new Employee(2, "Employee 3", LocalDate.parse("1994-01-15"), 35010, 3);
        assertEquals(employeeDao.getById(2), employee);
    }

    @Test
    public void testGetByBirthDate(){
        LocalDate date = LocalDate.parse("1994-01-15");
        Employee employee = new Employee(2, "Employee 3", date, 35010, 3);
        assertEquals(employeeDao.getById(2), employeeDao.getByBirthDate(date).get(0));
    }

    @Test
    public void getByBirthDateDiapason(){
        LocalDate dateFrom = LocalDate.parse("1994-01-01");
        LocalDate dateTo = LocalDate.parse("1995-01-01");

        assertEquals(employeeDao.getByBirthDateDiapason(dateFrom, dateTo).size(), 4);
    }

    @Test
    public void testGetByDepartmentId(){
        assertEquals(employeeDao.getByDepartmentId(4).size(), 2);
    }

}