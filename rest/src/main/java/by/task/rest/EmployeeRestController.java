package by.task.rest;

import by.task.model.Employee;
import by.task.service.EmployeeService;
import by.task.service.exception.BadParamException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

/**
 * Class Controller that handle REST requests are associated with employee entity
 */
@RestController
@RequestMapping("/restEmployee")
public class EmployeeRestController {

    private static final Logger LOGGER = LogManager.getLogger(EmployeeRestController.class);

    @Resource
    private EmployeeService employeeService;

    public void setEmployeeService(EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity add(@RequestBody Employee employee){
        long id;
        Date lol = employee.getBirthDate();
        employee.setBirthDate(new Date(lol.getYear(), lol.getMonth(), lol.getDate()));

        try{
            id = employeeService.add(employee);
            return new ResponseEntity(id, HttpStatus.CREATED);
        } catch(BadParamException ex){
            LOGGER.warn("BAD PARAMETERS WERE PASSED");
            return new ResponseEntity(ex.getMessage() + "\n-->" + ex.getObject(), HttpStatus.BAD_REQUEST);
        } catch(Exception ex){
            LOGGER.warn("SOME UNEXPECTED EXCEPTION\n" + ex.getStackTrace().toString());
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity update(@RequestBody Employee employee){
        Date lol = employee.getBirthDate();

        employee.setBirthDate(new Date(lol.getYear(), lol.getMonth(), lol.getDate()));

        try{
            employeeService.update(employee);
            LOGGER.error("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + employee);
            return new ResponseEntity("ok", HttpStatus.ACCEPTED);
        } catch(BadParamException ex){
            LOGGER.warn("BAD PARAMETERS WERE PASSED");
            return new ResponseEntity(ex.getMessage() + "\n-->" + ex.getObject(), HttpStatus.BAD_REQUEST);
        } catch(Exception ex){
            LOGGER.warn("SOME UNEXPECTED EXCEPTION\n" + ex.getStackTrace().toString());
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity remove(@PathVariable long id){
        try{
            employeeService.remove(id);
            return new ResponseEntity(id, HttpStatus.ACCEPTED);
        } catch(BadParamException ex){
            LOGGER.warn("BAD PARAMETERS WERE PASSED");
            return new ResponseEntity(ex.getMessage() + "\n-->" + ex.getObject(), HttpStatus.BAD_REQUEST);
        } catch(Exception ex){
            LOGGER.warn("SOME UNEXPECTED EXCEPTION\n" + ex.getStackTrace().toString());
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/employeeById/{id}", method = RequestMethod.GET)
    public ResponseEntity getById(@PathVariable long id){
        try{
            Employee employee = employeeService.getById(id);
            return new ResponseEntity(employee, HttpStatus.OK);
        } catch(BadParamException ex){
            LOGGER.warn("BAD PARAMETERS WERE PASSED");
            return new ResponseEntity(ex.getMessage() + "\n-->" + ex.getObject(), HttpStatus.BAD_REQUEST);
        } catch(Exception ex){
            LOGGER.warn("SOME UNEXPECTED EXCEPTION\n" + ex.getStackTrace().toString());
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/employeesByDepartmentId/{id}", method = RequestMethod.GET)
    public ResponseEntity getByDepartmentId(@PathVariable long id){
        try{
            List<Employee> employees = employeeService.getByDepartmentId(id);
            return new ResponseEntity(employees, HttpStatus.OK);
        } catch(BadParamException ex){
            LOGGER.warn("BAD PARAMETERS WERE PASSED");
            return new ResponseEntity(ex.getMessage() + "\n-->" + ex.getObject(), HttpStatus.BAD_REQUEST);
        } catch(Exception ex){
            LOGGER.warn("SOME UNEXPECTED EXCEPTION\n" + ex.getStackTrace().toString());
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getAll(){
        try{
            List<Employee> employees = employeeService.getAll();
            return new ResponseEntity(employees, HttpStatus.OK);
        } catch(Exception ex){
            LOGGER.warn("SOME UNEXPECTED EXCEPTION\n" + ex.getStackTrace().toString());
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
