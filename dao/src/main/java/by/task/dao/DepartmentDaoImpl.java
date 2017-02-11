package by.task.dao;

import by.task.model.Department;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import java.util.List;

/**
 *  Gives acces to DB
 */
public class DepartmentDaoImpl implements DepartmentDao {

    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${insert-department-path}')).inputStream)}")
    public String insertDepartmentSql;

    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${update-department-path}')).inputStream)}")
    public String updateDepartmentSql;

    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${delete-department-path}')).inputStream)}")
    public String deleteDepartmentSql;

    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${select-department-all-path}')).inputStream)}")
    public String selectAllDepartmentsSql;

    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${select-department-by-id-path}')).inputStream)}")
    public String selectDepartmentByIdSql;

    private static final String DEPARTMENT_ID = "departmentId";
    private static final String DEPARTMENT_NAME = "departmentName";

    private static Logger LOGGER = LogManager.getLogger(DepartmentDaoImpl.class);

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    /**
     *
     * @param department - object that need to  add. id generates automatically.
     * @return - id of new object
     */
    @Override
    public long add(Department department) {
        LOGGER.debug("INSERT ->" + department);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue(DEPARTMENT_ID, department.getDepartmentId())
                .addValue(DEPARTMENT_NAME, department.getDepartmentName());

        namedParameterJdbcTemplate.update(insertDepartmentSql, parameters, keyHolder);

        long id = (Long) keyHolder.getKey();
        LOGGER.debug("ID OF INSERTED OBJECT -> " + id);

        return id;
    }

    /**
     *
     *  * @param department - object that stores necessaries data for updating.
     */
    @Override
    public void update(Department department) {
        LOGGER.debug("UPDATE ->" + department);

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue(DEPARTMENT_ID, department.getDepartmentId())
                .addValue(DEPARTMENT_NAME, department.getDepartmentName());

        namedParameterJdbcTemplate.update(updateDepartmentSql, parameters);
    }

    /**
     *
     * @param id - number of object that should be removed.
     */
    @Override
    public void remove(long id) {
        LOGGER.debug("REMOVE ->" + id);

        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue(DEPARTMENT_ID, id);

        namedParameterJdbcTemplate.update(deleteDepartmentSql, parameterSource);
    }

    /**
     *
     * @return - list of all departments
     */
    @Override
    public List<Department> getAll() {

        return namedParameterJdbcTemplate.query(selectAllDepartmentsSql, new DepartmentMapper());
    }

    /**
     *
     * @param departmentId - number of department that should be selected
     * @return - department by id
     */
    @Override
    public Department getById(long departmentId) {
        LOGGER.debug("SELECT -> " + departmentId);

        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue(DEPARTMENT_ID, departmentId);

        return namedParameterJdbcTemplate.queryForObject(selectDepartmentByIdSql, sqlParameterSource, new DepartmentMapper());
    }

    public class DepartmentMapper implements RowMapper<Department> {
        @Override
        public Department mapRow(ResultSet rs, int i) throws SQLException {
            Department Department = new Department();

            Department.setDepartmentId(rs.getInt("departmentId"));
            Department.setDepartmentName(rs.getString("departmentName"));
            return Department;
        }
    }
}
