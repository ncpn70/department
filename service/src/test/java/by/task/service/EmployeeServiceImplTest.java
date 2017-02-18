package by.task.service;

import by.task.model.Employee;
import by.task.service.exception.BadParamException;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
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
public class EmployeeServiceImplTest {

    @Autowired
    EmployeeService employeeService;

    @Test
    public void add() throws Exception {
        Employee employee1 = new Employee(0, "EMPLOYEE TEST", new Date(2001, 1, 1), 100, 2);

        Throwable ex = catchThrowable(() -> {
            employeeService.add(null);
        });

        assertThat(ex).isInstanceOf(BadParamException.class);

        Throwable e = catchThrowable(() -> {
            employeeService.add(new Employee(0, null, new Date(2001, 1, 1), 100, 2));
        });

        assertThat(e).isInstanceOf(BadParamException.class);

        Throwable e1 = catchThrowable(() -> {
            employeeService.add(new Employee(0, "EMPLOYEE TEST", null, 100, 2));
        });

        assertThat(e1).isInstanceOf(BadParamException.class);

        Throwable e3 = catchThrowable(() -> {
            employeeService.add(new Employee(0, "EMPLOYEE TEST", null, 100, -3));
        });

        assertThat(e3).isInstanceOf(BadParamException.class);

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

        Throwable ex4 = catchThrowable(() -> {
            employeeService.update(new Employee(0, null, new Date(2001, 1, 1), 100, 2));
        });

        assertThat(ex4).isInstanceOf(BadParamException.class);

        Throwable ex = catchThrowable(() -> {
            employeeService.update(null);
        });

        assertThat(ex).isInstanceOf(BadParamException.class);

        Throwable e = catchThrowable(() -> {
            employeeService.update(new Employee(0, null, new Date(2001, 1, 1), 100, 2));
        });

        assertThat(e).isInstanceOf(BadParamException.class);

        Throwable e1 = catchThrowable(() -> {
            employeeService.update(new Employee(0, "EMPLOYEE TEST", null, 100, 2));
        });

        assertThat(e1).isInstanceOf(BadParamException.class);

        Throwable e3 = catchThrowable(() -> {
            employeeService.update(new Employee(0, "EMPLOYEE TEST", null, 100, -3));
        });

        assertThat(e3).isInstanceOf(BadParamException.class);

        employeeService.update(employee1);
        Employee employee2 = employeeService.getById(1);


        assertEquals(employee1, employee2);
    }

    @Test
    public void remove() throws Exception {
        long id = 1;

        employeeService.remove(id);

        Throwable ex2 = catchThrowable(() -> {
            employeeService.remove(-5);
        });

        assertThat(ex2).isInstanceOf(BadParamException.class);


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
        Employee employee = new Employee(2, "Employee 3", new Date(94, 0, 15), 35010, 3);

        Throwable ex = catchThrowable(() -> {
            employeeService.getById(-6);
        });

        assertThat(ex).isInstanceOf(BadParamException.class);

        assertEquals(employeeService.getById(2), employee);
    }

    @Test
    public void getByDepartmentId() throws Exception {

        Throwable ex = catchThrowable(() -> {
            employeeService.getByDepartmentId(-6);
        });

        assertThat(ex).isInstanceOf(BadParamException.class);

        assertEquals(employeeService.getByDepartmentId(4).size(), 2);
    }

}