package by.task.service;

import by.task.model.Department;

import java.util.List;

/**
 * Created by simpson on 12.2.17.
 */
public interface DepartmentService {
    /**
     *
     * @param department Department object that need to create ne department. id generates automatically.
     * @return id of new object
     */
    long add(Department department);

    /**
     *
     *  * @param department object that stores necessaries data for updating.
     */
    void update(Department department);

    /**
     *
     * @param departmentId number of object that should be removed.
     */
    void remove(long departmentId);

    /**
     *
     * @return list of all departments
     */
    List<Department> getAll();

    /**
     *
     * @param departmentId - number of department that should be selected
     * @return department by id
     */
    Department getById(long departmentId);
}
