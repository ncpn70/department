package by.task.service;

import by.task.model.Employee;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by simpson on 12.2.17.
 */
public interface EmployeeService {
    /**
     *
     * @param employee object to add. Id generates automatically
     * @return
     */
    long add(Employee employee);

    /**
     *
     * @param employee object for updating
     */
    void update(Employee employee);

    /**
     *
     * @param employeeId number of object that should be removed
     */
    void remove(long employeeId);

    void removeByDepartmentId(long departmentId);

    /**
     *
     * @return list of all employees
     */
    List<Employee> getAll();

    /**
     *
     * @param employeeId number of needed employee
     * @return employee by id
     */
    Employee getById(long employeeId);

    /**
     *
     * @param departmentId id of department that poses of employee
     * @return list of employees by id of department
     */
    List<Employee> getByDepartmentId(long departmentId);
}
