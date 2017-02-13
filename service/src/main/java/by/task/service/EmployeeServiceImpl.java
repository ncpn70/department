package by.task.service;

import by.task.dao.EmployeeDao;
import by.task.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by simpson on 12.2.17.
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeDao employeeDao;
    /**
     * @param employee object to add. Id generates automatically
     * @return
     */
    @Override
    public long add(Employee employee) {
        return employeeDao.add(employee);
    }

    /**
     * @param employee object for updating
     */
    @Override
    public void update(Employee employee) {
        employeeDao.update(employee);
    }

    /**
     * @param employeeId number of object that should be removed
     */
    @Override
    public void remove(long employeeId) {
        employeeDao.remove(employeeId);
    }

    @Override
    public void removeByDepartmentId(long departmentId) {
        employeeDao.removeByDepartmentId(departmentId);
    }

    /**
     * @return list of all employees
     */
    @Override
    public List<Employee> getAll() {
        return employeeDao.getAll();
    }

    /**
     * @param employeeId number of needed employee
     * @return employee by id
     */
    @Override
    public Employee getById(long employeeId) {
        return employeeDao.getById(employeeId);
    }

    /**
     * @param fullName name of needed employee
     * @return list of employees by name
     */
    @Override
    public List<Employee> getByFullName(String fullName) {
        return employeeDao.getByFullName(fullName);
    }

    /**
     * @param date birthDate of employee for selection
     * @return list of employees by birthDate
     */
    @Override
    public List<Employee> getByBirthDate(java.sql.Date date) {
        return employeeDao.getByBirthDate(date);
    }

    /**
     * @param from low border of diapason
     * @param to   top border of diapason
     * @return list of employees by birthDate diapason
     */
    @Override
    public List<Employee> getByBirthDateDiapason(java.sql.Date from, java.sql.Date to) {
        return employeeDao.getByBirthDateDiapason(from, to);
    }

    /**
     * @param departmentId id of department that poses of employee
     * @return list of employees by id of department
     */
    @Override
    public List<Employee> getByDepartmentId(long departmentId) {
        return employeeDao.getByDepartmentId(departmentId);
    }
}
