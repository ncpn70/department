package by.task.dao;

import by.task.model.Department;
import by.task.model.Employee;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * Allow to perform CRUD operations regarding Department entity
 */
@Repository
public class EmployeeDaoImpl implements EmployeeDao {

    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${insert-employee-path}')).inputStream)}")
    public String insertEmployeeSql;

    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${update-employee-path}')).inputStream)}")
    public String updateEmployeeSql;

    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${delete-employee-path}')).inputStream)}")
    public String deleteEmployeeSql;

    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${delete-employee-by-department-id-path}')).inputStream)}")
    public String deleteEmployeeByDepartmentIdSql;

    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${select-employee-all-path}')).inputStream)}")
    public String selectAllEmployeesSql;

    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${select-employee-by-id-path}')).inputStream)}")
    public String selectEmployeeByIdSql;

    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${select-employee-by-fullname-path}')).inputStream)}")
    public String selectEmployeeByFullNameSql;

    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${select-employee-by-birthDate-path}')).inputStream)}")
    public String selectEmployeeByBirthDateSql;

    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${select-employee-by-birthDate-diapason-path}')).inputStream)}")
    public String selectEmployeeByBirthDateDiapasonSql;

    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${select-employee-by-department-id-path}')).inputStream)}")
    public String selectEmployeeByDepartmentIdSql;

    private static String EMPLOYEE_ID = "employeeId";
    private static String FULL_NAME = "fullName";
    private static String BIRTH_DATE = "birthDate";
    private static String DEPARTMENT_ID = "departmentId";
    private static String SALARY = "salary";

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static Logger LOGGER = LogManager.getLogger(DepartmentDaoImpl.class);

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    public void setDataSource() {
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    /**
     *
     * @param employee object to add. Id generates automatically
     * @return
     */
    @Override
    public long add(Employee employee) {
        LOGGER.debug("INSERT -> " + employee);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue(EMPLOYEE_ID, employee.getEmployeeId())
                .addValue(FULL_NAME, employee.getFullName())
                .addValue(BIRTH_DATE, employee.getBirthDate())
                .addValue(SALARY, employee.getSalary())
                .addValue(DEPARTMENT_ID, employee.getDepartmentId());

        namedParameterJdbcTemplate.update(insertEmployeeSql, sqlParameterSource, keyHolder);

        long id = (Long) keyHolder.getKey();

        LOGGER.debug("NEW EMPLOYEE ID -> " + employee);

        return id;
    }

    /**
     *
     * @param employee object for updating
     */
    @Override
    public void update(Employee employee) {
        LOGGER.debug("UPDATE -> " + employee);

        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue(EMPLOYEE_ID, employee.getEmployeeId())
                .addValue(FULL_NAME, employee.getFullName())
                .addValue(BIRTH_DATE, employee.getBirthDate())
                .addValue(SALARY, employee.getSalary())
                .addValue(DEPARTMENT_ID, employee.getDepartmentId());

        namedParameterJdbcTemplate.update(updateEmployeeSql, sqlParameterSource);
    }

    /**
     *
     * @param employeeId number of object that should be removed
     */
    @Override
    public void remove(long employeeId) {
        LOGGER.debug("REMOVE ->" + employeeId);

        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue(EMPLOYEE_ID, employeeId);

        namedParameterJdbcTemplate.update(deleteEmployeeSql, sqlParameterSource);
    }

    @Override
    public void removeByDepartmentId(long departmentId) {
        LOGGER.debug("REMOVE BY DEPARTMENT ->" + departmentId);

        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue(DEPARTMENT_ID, departmentId);

        namedParameterJdbcTemplate.update(deleteEmployeeByDepartmentIdSql, sqlParameterSource);
    }

    /**
     *
     * @return list of all employees
     */
    @Override
    public List<Employee> getAll() {
        return namedParameterJdbcTemplate.query(selectAllEmployeesSql, this::mapEmployee);
    }

    /**
     *
     * @param employeeId number of needed employee
     * @return employee by id
     */
    @Override
    public Employee getById(long employeeId) {
        LOGGER.debug("SELECT BY ID -> " + employeeId);

        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue(EMPLOYEE_ID, employeeId);

        return namedParameterJdbcTemplate.queryForObject(selectEmployeeByIdSql, sqlParameterSource, this::mapEmployee);
    }

    /**
     *
     * @param departmentId id of department that poses of employee
     * @return list of employees by id of department
     */
    @Override
    public List<Employee> getByDepartmentId(long departmentId) {
        LOGGER.debug("SELECT BY DEPARTMENT -> " + departmentId);

        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue(DEPARTMENT_ID, departmentId);


        return namedParameterJdbcTemplate.query(selectEmployeeByDepartmentIdSql, sqlParameterSource,
                this::mapEmployee);
    }

    /**
     *      lambdas except RowMapper that is used in DepartmentDaoImpl
     */
    private Employee mapEmployee(ResultSet rs, int i) throws SQLException{
        return new Employee(rs.getLong("employeeId"),
                rs.getString("fullName"), rs.getDate("birthDate"),
                rs.getLong("salary"), rs.getLong("departmentId"));
    }

}