package by.task.dao;

import by.task.model.Department;

import java.util.List;

/**
 * Created by simpson on 7.2.17.
 */
public interface DepartmentDao {
    long add(Department department);
    void update(Department department);
    void remove(long departmentId);
    List<Department> getAll();
    Department getById(long departmentId);
}
