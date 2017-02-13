package by.task.service;

import by.task.dao.DepartmentDao;
import by.task.dao.EmployeeDao;
import by.task.model.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by simpson on 12.2.17.
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    DepartmentDao departmentDao;

    @Autowired
    EmployeeDao employeeDao;
    /**
     * @param department Department object that need to create ne department. id generates automatically.
     * @return id of new object
     */
    @Override
    public long add(Department department) {
        return departmentDao.add(department);
    }

    /**
     * * @param department object that stores necessaries data for updating.
     *
     * @param department
     */
    @Override
    public void update(Department department) {
        departmentDao.update(department);
    }

    /**
     * @param departmentId number of object that should be removed.
     */
    @Override
    @Transactional
    public void remove(long departmentId) {
        employeeDao.removeByDepartmentId(departmentId);
        departmentDao.remove(departmentId);
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
        return departmentDao.getById(departmentId);
    }
}
