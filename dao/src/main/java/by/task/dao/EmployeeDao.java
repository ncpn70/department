package by.task.dao;

import by.task.model.Employee;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by simpson on 7.2.17.
 */
public interface EmployeeDao {

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
     * @param fullName name of needed employee
     * @return list of employees by name
     */
    List<Employee> getByFullName(String fullName);

    /**
     *
     * @param date birthDate of employee for selection
     * @return list of employees by birthDate
     */
    List<Employee> getByBirthDate(java.sql.Date date);

    /**
     *
     * @param from low border of diapason
     * @param to top border of diapason
     * @return list of employees by birthDate diapason
     */
    List<Employee> getByBirthDateDiapason(java.sql.Date from, java.sql.Date to);

    /**
     *
     * @param departmentId id of department that poses of employee
     * @return list of employees by id of department
     */
    List<Employee> getByDepartmentId(long departmentId);
}
