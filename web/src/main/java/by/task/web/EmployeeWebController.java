package by.task.web;

import by.task.model.Department;
import by.task.model.Employee;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Date;
import java.util.List;

/**
 * Created by simpson on 14.2.17.
 */
@Controller
@RequestMapping("/employees")
public class EmployeeWebController {

    private static final Logger LOGGER = LogManager.getLogger(DepartmentWebController.class);
    private static String URL = "http://localhost:8080/rest/restEmployee/";

    @Autowired
    private RestTemplate restTemplate;


    @RequestMapping("/")
    public ModelAndView employeePage(){
        List<Employee> list = restTemplate.getForObject(URL, List.class);

        ModelAndView modelAndView = new ModelAndView("employeePage", "employees", list);

        return modelAndView;
    }

    @RequestMapping("/addEmployee")
    public ModelAndView addEmployee(@RequestParam("fullName") String fullName,
                                    @RequestParam("date") Date date,
                                    @RequestParam("salary") long salary,
                                    @RequestParam("departmentId") long departmentId){
        Employee employee = new Employee(0, fullName, date, salary, departmentId);
        ModelAndView modelAndView = null;

        try{
            long i = restTemplate.postForObject(URL, employee, long.class);
            modelAndView = new ModelAndView("redirect:/employees/");
        } catch (HttpClientErrorException ex) {
            LOGGER.error("HttpClientErrorException -->" + ex.getStatusCode());
            modelAndView = new ModelAndView("employeePage", "employees", restTemplate.getForObject(URL, List.class));
            modelAndView.addObject("error", "HttpClientErrorException occurred");
        }catch(Exception ex){
            LOGGER.error("Exception -->" + ex.getStackTrace());
            modelAndView = new ModelAndView("employeePage", "employees", restTemplate.getForObject(URL, List.class));
            modelAndView.addObject("creationError", true);
            modelAndView.addObject("error", ex.toString());
        }

        return modelAndView;
    }

    @RequestMapping("/updateEmployee")
    public ModelAndView updateEmployee(@RequestParam("employeeId") long employeeId,
                                        @RequestParam("fullName") String fullName,
                                        @RequestParam("date") Date date,
                                        @RequestParam("salary") long salary,
                                        @RequestParam("departmentId") long departmentId){
        LOGGER.error("!!!!!!!!!!!!fullName" + fullName);
        Employee employee = new Employee(employeeId, fullName, date, salary, departmentId);

        ModelAndView modelAndView = null;

        try{
            restTemplate.put(URL, employee);
            modelAndView = new ModelAndView("redirect:/employees/");
        } catch (HttpClientErrorException ex) {
            LOGGER.error("HttpClientErrorException -->" + ex.getStatusCode());
            modelAndView = new ModelAndView("employeePage", "employees", restTemplate.getForObject(URL, List.class));
            modelAndView.addObject("error", "HttpClientErrorException occurred");
        }catch(Exception ex){
            LOGGER.error("Exception -->" + ex.getStackTrace());
            modelAndView = new ModelAndView("employeePage", "employees", restTemplate.getForObject(URL, List.class));
            modelAndView.addObject("creationError", true);
            modelAndView.addObject("error", ex.toString());
        }

        return modelAndView;
    }

    @RequestMapping("/employeesOfDepartment")
    public ModelAndView employeesOfDepartment(@RequestParam("departmentId") long departmentId){
        List<Employee> employees = null;
        ModelAndView modelAndView = null;

        try{
            employees = restTemplate.getForObject(URL + "/employeesByDepartmentId/" + departmentId, List.class);
            LOGGER.error("");
            modelAndView = new ModelAndView("employeePage", "employees", employees);
        } catch (HttpClientErrorException ex) {
            LOGGER.error("HttpClientErrorException -->" + ex.getStatusCode());
            modelAndView = new ModelAndView("employeePage", "employees", restTemplate.getForObject(URL, List.class));
            modelAndView.addObject("error", "HttpClientErrorException occurred");
        }catch(Exception ex){
            LOGGER.error("Exception -->" + ex.getStackTrace());
            modelAndView = new ModelAndView("employeePage", "employees", restTemplate.getForObject(URL, List.class));
            modelAndView.addObject("creationError", true);
            modelAndView.addObject("error", ex.toString());
        }

        return modelAndView;
    }

    @RequestMapping("/removeEmployee")
    public ModelAndView removeEmployee(@RequestParam("employeeId") long depId){
        ModelAndView modelAndView = null;

        try {
            restTemplate.delete(URL + depId, depId);
            modelAndView = new ModelAndView("redirect:/employees/");
        } catch (HttpClientErrorException ex) {
            LOGGER.error("HttpClientErrorException -->" + ex.getStatusCode());
            modelAndView = new ModelAndView("employeePage", "employees", restTemplate.getForObject(URL, List.class));
            modelAndView.addObject("error", "HttpClientErrorException occurred");
        }catch(Exception ex){
            LOGGER.error("Exception -->" + ex.getStackTrace());
            modelAndView = new ModelAndView("employeePage", "employees", restTemplate.getForObject(URL, List.class));
            modelAndView.addObject("creationError", true);
            modelAndView.addObject("error", ex.toString());
        }

        return modelAndView;
    }

}
