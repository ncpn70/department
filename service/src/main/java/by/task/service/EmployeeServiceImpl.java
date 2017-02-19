package by.task.service;

import by.task.dao.EmployeeDao;
import by.task.model.Employee;
import by.task.service.exception.BadParamException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

/**
 * Class of Service layer that serve employee entity, and should
 * consist business logic
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeDao employeeDao;

    private static Logger LOGGER = LogManager.getLogger(DepartmentServiceImpl.class);
    private static final String BAD_PARAMETER_MSG = "Bad parameters exception occurred. Wrong or null parameters were passed";

    /**
     * @param employee object to add. Id generates automatically
     * @return
     */
    @Override
    public long add(Employee employee) {
        try {
            Assert.notNull(employee);
            Assert.notNull(employee.getBirthDate());
            Assert.notNull(employee.getFullName());
            Assert.isTrue(employee.getDepartmentId() >= 0);
            return employeeDao.add(employee);
        }catch(IllegalArgumentException e){
            LOGGER.error(BAD_PARAMETER_MSG + "\n" + employee);
            throw new BadParamException(BAD_PARAMETER_MSG, employee);
        }
    }

    /**
     * @param employee object for updating
     */
    @Override
    public void update(Employee employee) {
        try {
            Assert.notNull(employee);
            Assert.isTrue(employee.getEmployeeId() >= 0);
            Assert.notNull(employee.getBirthDate());
            Assert.notNull(employee.getFullName());
            Assert.isTrue(employee.getSalary() > 0);
            Assert.isTrue(employee.getDepartmentId() >= 0);
            employeeDao.update(employee);
        }catch(IllegalArgumentException e){
            LOGGER.error(BAD_PARAMETER_MSG + "\n" + employee);
            throw new BadParamException(BAD_PARAMETER_MSG, employee);
        }
    }

    /**
     * @param employeeId number of object that should be removed
     */
    @Override
    public void remove(long employeeId) {
        try {
            Assert.isTrue(employeeId >= 0);
            employeeDao.remove(employeeId);
        } catch(IllegalArgumentException e){
            LOGGER.error(BAD_PARAMETER_MSG + "\n" + employeeId);
            throw new BadParamException(BAD_PARAMETER_MSG, employeeId);
        }
    }

    @Override
    public void removeByDepartmentId(long departmentId) {
        try {
            Assert.isTrue(departmentId >= 0);
            employeeDao.removeByDepartmentId(departmentId);
        } catch(IllegalArgumentException e){
            LOGGER.error(BAD_PARAMETER_MSG + "\n" + departmentId);
            throw new BadParamException(BAD_PARAMETER_MSG, departmentId);
        }
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
        try {
            Assert.isTrue(employeeId >= 0);
            return employeeDao.getById(employeeId);
        } catch(IllegalArgumentException e){
            LOGGER.error(BAD_PARAMETER_MSG + "\n" + employeeId);
            throw new BadParamException(BAD_PARAMETER_MSG, employeeId);
        }

    }

    /**
     * @param departmentId id of department that poses of employee
     * @return list of employees by id of department
     */
    @Override
    public List<Employee> getByDepartmentId(long departmentId) {
        try {
            Assert.isTrue(departmentId >= 0);
        return employeeDao.getByDepartmentId(departmentId);
        } catch(IllegalArgumentException e){
            LOGGER.error(BAD_PARAMETER_MSG + "\n" + departmentId);
            throw new BadParamException(BAD_PARAMETER_MSG, departmentId);
        }
    }
}
