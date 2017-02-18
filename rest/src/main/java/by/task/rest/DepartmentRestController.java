package by.task.rest;

import by.task.model.Department;
import by.task.service.DepartmentService;
import by.task.service.exception.BadParamException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by simpson on 12.2.17.
 */
@RestController
@RequestMapping("/restDepartment")
public class DepartmentRestController {

    private static Logger LOGGER = LogManager.getLogger(DepartmentRestController.class);

    @Resource
    private DepartmentService departmentService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity add(@RequestBody Department department){

        long id = 0;
        try{
            id = departmentService.add(department);
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
    public ResponseEntity update(@RequestBody Department department){
        long id = 0;
        try{
            departmentService.update(department);
            return new ResponseEntity(department, HttpStatus.ACCEPTED);
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
            departmentService.remove(id);
            return new ResponseEntity(id, HttpStatus.ACCEPTED);
        } catch(BadParamException ex){
            LOGGER.warn("BAD PARAMETERS WERE PASSED");
            return new ResponseEntity(ex.getMessage() + "\n-->" + ex.getObject(), HttpStatus.BAD_REQUEST);
        } catch(Exception ex){
            LOGGER.warn("SOME UNEXPECTED EXCEPTION\n" + ex.getStackTrace().toString());
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List> getAll(){
        try {
            List<Department> list = departmentService.getAll();
            return new ResponseEntity<List>(list, HttpStatus.OK);
        } catch(Exception ex){
            LOGGER.warn("SOME UNEXPECTED EXCEPTION\n" + ex.getStackTrace().toString());
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/departmentById/{id}", method = RequestMethod.GET)
    public ResponseEntity getdepartmentById(@PathVariable long id){

        try{
            Department department = departmentService.getById(id);
            return new ResponseEntity(department, HttpStatus.OK);
        } catch(BadParamException ex){
            LOGGER.warn("BAD PARAMETERS WERE PASSED");
            return new ResponseEntity(ex.getMessage() + "\n-->" + ex.getObject(), HttpStatus.BAD_REQUEST);
        } catch(Exception ex){
            LOGGER.warn("SOME UNEXPECTED EXCEPTION\n" + ex.getStackTrace().toString());
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
