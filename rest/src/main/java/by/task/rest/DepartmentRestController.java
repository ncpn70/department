package by.task.rest;

import by.task.model.Department;
import by.task.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * Created by simpson on 12.2.17.
 */
@Controller
@RequestMapping("/restDepartment")
public class DepartmentRestController {

    @Resource
    private DepartmentService departmentService;

    public void setDepartmentService(DepartmentService departmentService){
        this.departmentService = departmentService;
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity add(@RequestBody Department department){

        long id = 0;
        try{
            id = departmentService.add(department);
            return new ResponseEntity(id, HttpStatus.CREATED);
        } catch(Exception ex){
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
     }


    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity update(@RequestBody Department department){
        long id = 0;
        try{
            departmentService.update(department);
            return new ResponseEntity(department, HttpStatus.ACCEPTED);
        } catch(Exception ex){
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity remove(@PathVariable long id){
        try{
            departmentService.remove(id);
            return new ResponseEntity(id, HttpStatus.ACCEPTED);
        } catch(Exception ex){
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getAll(){
        List<Department> list = departmentService.getAll();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/departmentById/{id}", method = RequestMethod.GET)
    public ResponseEntity getdepartmentById(@PathVariable long id){

        try{
            Department department = departmentService.getById(id);
            return new ResponseEntity(department, HttpStatus.OK);
        } catch (Exception ex){
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
