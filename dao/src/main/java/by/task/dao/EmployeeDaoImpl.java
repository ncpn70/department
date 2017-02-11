package by.task.dao;

import by.task.model.Employee;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by simpson on 7.2.17.
 */
public class EmployeeDaoImpl implements EmployeeDao {

    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${insert-employee-path}')).inputStream)}")
    public String insertEmployeeSql;

    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${update-employee-path}')).inputStream)}")
    public String updateEmployeeSql;

    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${delete-employee-path}')).inputStream)}")
    public String deleteEmployeeSql;

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

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }


    @Override
    public long add(Employee employee) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue(EMPLOYEE_ID, employee.getEmployeeId())
                .addValue(FULL_NAME, employee.getFullName())
                .addValue(BIRTH_DATE, employee.getBirthDate())
                .addValue(DEPARTMENT_ID, employee.getDepartmentId());

        long id = namedParameterJdbcTemplate.update(insertEmployeeSql, sqlParameterSource, keyHolder);

        return id;
    }

    @Override
    public void update(Employee employee) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue(EMPLOYEE_ID, employee.getEmployeeId())
                .addValue(FULL_NAME, employee.getFullName())
                .addValue(BIRTH_DATE, employee.getBirthDate())
                .addValue(DEPARTMENT_ID, employee.getDepartmentId());

        namedParameterJdbcTemplate.update(updateEmployeeSql, sqlParameterSource);
    }

    @Override
    public void remove(long employeeId) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue(EMPLOYEE_ID, employeeId);

        namedParameterJdbcTemplate.update(deleteEmployeeSql, sqlParameterSource);
    }

    @Override
    public List<Employee> getAll() {
        return namedParameterJdbcTemplate.query(selectAllEmployeesSql, new EmployeeMapper());
    }

    @Override
    public Employee getById(long employeeId) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue(EMPLOYEE_ID, employeeId);


        return namedParameterJdbcTemplate.queryForObject(selectEmployeeByIdSql, sqlParameterSource, new EmployeeMapper());
    }

    @Override
    public List<Employee> getByFullName(String fullName) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue(FULL_NAME, fullName);


        return namedParameterJdbcTemplate.query(selectEmployeeByFullNameSql, sqlParameterSource, new EmployeeMapper());
    }

    @Override
    public List<Employee> getByBirthDate(LocalDate date) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue(BIRTH_DATE, date);

        return namedParameterJdbcTemplate.query(selectEmployeeByBirthDateSql, sqlParameterSource, new EmployeeMapper());
    }

    @Override
    public List<Employee> getByBirthDateDiapason(LocalDate from, LocalDate to) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue(BIRTH_DATE + 1, from)
                .addValue(BIRTH_DATE + 2, to);

        return  namedParameterJdbcTemplate.query(selectEmployeeByBirthDateDiapasonSql, sqlParameterSource, new EmployeeMapper());
    }

    @Override
    public List<Employee> getByDepartmentId(long departmentId) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue(DEPARTMENT_ID, departmentId);


        return namedParameterJdbcTemplate.query(selectEmployeeByDepartmentIdSql, sqlParameterSource, new EmployeeMapper());
    }

    public class EmployeeMapper implements RowMapper<Employee> {
        @Override
        public Employee mapRow(ResultSet rs, int i) throws SQLException {
            Employee employee = new Employee();

            employee.setEmployeeId(rs.getInt("employeeId"));
            employee.setFullName(rs.getString("fullName"));
            employee.setBirthDate(rs.getDate("birthDate").toLocalDate());
            employee.setSalary(rs.getLong("salary"));
            employee.setDepartmentId(rs.getLong("departmentId"));

            return employee;
        }
    }
}