package by.task.service;

import by.task.model.Employee;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

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
@Ignore
public class EmployeeServiceImplTest {

    @Autowired
    EmployeeService employeeService;

  /*  @Test
    public void add() throws Exception {
        Employee employee1 = new Employee(0, "EMPLOYEE TEST", LocalDate.now(), 100, 2);

        assertEquals(5, employeeService.add(employee1));

        Employee employee2 = employeeService.getById(5);
        employee2.setEmployeeId(0);
        assertEquals(employee1, employee2);
    }

    @Test
    public void update() throws Exception {
        Employee employee1 = employeeService.getById(1);
        employee1.setSalary(10004);
        employee1.setDepartmentId(5);
        employee1.setFullName("VIK");

        employeeService.update(employee1);
        Employee employee2 = employeeService.getById(1);


        assertEquals(employee1, employee2);
    }

    @Test
    public void remove() throws Exception {
        long id = 1;

        employeeService.remove(id);

        Throwable ex = catchThrowable(() -> {
            employeeService.getById(id);
        });

        assertThat(ex).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    public void getAll() throws Exception {
        assertEquals(employeeService.getAll().size(), 5);
    }

    @Test
    public void getById() throws Exception {
        Employee employee = new Employee(2, "Employee 3", LocalDate.parse("1994-01-15"), 35010, 3);
        assertEquals(employeeService.getById(2), employee);
    }

    @Test
    public void getByBirthDate() throws Exception {
        LocalDate date = LocalDate.parse("1994-01-15");
        Employee employee = new Employee(2, "Employee 3", date, 35010, 3);
        assertEquals(employeeService.getById(2), employeeService.getByBirthDate(date).get(0));
    }

    @Test
    public void getByBirthDateDiapason() throws Exception {
        LocalDate dateFrom = LocalDate.parse("1994-01-01");
        LocalDate dateTo = LocalDate.parse("1995-01-01");

        assertEquals(employeeService.getByBirthDateDiapason(dateFrom, dateTo).size(), 4);
    }

    @Test
    public void getByDepartmentId() throws Exception {
        assertEquals(employeeService.getByDepartmentId(4).size(), 2);
    }*/

}