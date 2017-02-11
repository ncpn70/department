package by.task.dao;

import by.task.model.Employee;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by simpson on 7.2.17.
 */
public interface EmployeeDao {

    long add(Employee employee);
    void update(Employee employee);
    void remove(long employeeId);
    List<Employee> getAll();
    Employee getById(long employeeId);
    List<Employee> getByFullName(String fullName);
    List<Employee> getByBirthDate(LocalDate date);
    List<Employee> getByBirthDateDiapason(LocalDate from, LocalDate to);
    List<Employee> getByDepartmentId(long departmentId);
}
