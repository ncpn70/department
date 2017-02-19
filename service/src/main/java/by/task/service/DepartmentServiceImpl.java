package by.task.service;

import by.task.dao.DepartmentDao;
import by.task.dao.DepartmentDaoImpl;
import by.task.dao.EmployeeDao;
import by.task.model.Department;
import by.task.service.exception.BadParamException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Class of Service layer that serve department entity, and should
 * consist business logic/
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {

    private static Logger LOGGER = LogManager.getLogger(DepartmentServiceImpl.class);
    private static final String BAD_PARAMETER_MSG = "Bad parameters exception occurred. Wrong or null parameters were passed";

    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    private EmployeeDao employeeDao;
    /**
     * @param department Department object that need to create ne department. id generates automatically.
     * @return id of new object
     */
    @Override
    public long add(Department department) {

        LOGGER.error("starts with " + department);

        try {
            Assert.notNull(department);
            Assert.notNull(department.getDepartmentName());
            return departmentDao.add(department);
        } catch(IllegalArgumentException e){
            LOGGER.error(BAD_PARAMETER_MSG + "\n" + department);
            throw new BadParamException(BAD_PARAMETER_MSG, department);
        }
    }

    /**
     *
     * @param department object that stores necessaries data for updating.
     */
    @Override
    public void update(Department department) {
        try {
            Assert.notNull(department);
            Assert.isTrue(department.getDepartmentId() >= 0);
            Assert.notNull(department.getDepartmentName());
            departmentDao.update(department);
        } catch (IllegalArgumentException e){
            LOGGER.error(BAD_PARAMETER_MSG + "\n" + department);
            throw new BadParamException(BAD_PARAMETER_MSG, department);
        }
    }

    /**
     * @param departmentId number of object that should be removed.
     */
    @Override
    @Transactional
    public void remove(long departmentId) {
        try {
            Assert.isTrue(departmentId >= 0);
            employeeDao.removeByDepartmentId(departmentId);
            departmentDao.remove(departmentId);
        } catch(IllegalArgumentException e){
            LOGGER.error(BAD_PARAMETER_MSG + "\n" + departmentId);
            throw new BadParamException(BAD_PARAMETER_MSG, departmentId);
        }
    }

    /**
     * @return list of all departments
     */
    @Override
    public List<Department> getAll() {
        return departmentDao.getAll();
    }

    /**
     * @param departmentId - number of department that should be selected
     * @return department by id
     */
    @Override
    public Department getById(long departmentId) {
        try{
            Assert.isTrue(departmentId >= 0);
            return departmentDao.getById(departmentId);
        } catch(IllegalArgumentException e){
            LOGGER.error(BAD_PARAMETER_MSG + "\n" + departmentId);
            throw new BadParamException(BAD_PARAMETER_MSG, departmentId);
        }
    }
}
