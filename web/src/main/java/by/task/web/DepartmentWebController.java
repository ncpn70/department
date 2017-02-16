package by.task.web;

import by.task.model.Department;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by simpson on 13.2.17.
 */
@Controller
@RequestMapping("/departments")
public class DepartmentWebController {
    @Resource
    private RestTemplate restTemplate;

//    private void init(){
//        restTemplate.setMessageConverters();
//    }
    private static final Logger LOGGER = LogManager.getLogger(DepartmentWebController.class);

    private static String URL = "http://localhost:8080/rest/restDepartment/";

    @RequestMapping("/")
    public ModelAndView departmentPage(){
        List<Department> list = restTemplate.getForObject(URL, List.class);

        ModelAndView modelAndView = new ModelAndView("departmentPage", "departments", list);

        LOGGER.error("controller" + modelAndView + "\n" + list);
        return modelAndView;
    }

    @RequestMapping("/addDepartment")
    public ModelAndView addDeprtment(@RequestParam("name") String name){
        Department department = new Department(name, 0L);
        ModelAndView modelAndView = null;

        try{
            long i = restTemplate.postForObject(URL, department, long.class);
            modelAndView = new ModelAndView("redirect:/departments/");
        } catch(Exception ex){
            modelAndView = new ModelAndView("departmentPage", "departments", restTemplate.getForObject(URL, List.class));
            modelAndView.addObject("creationError", true);
            modelAndView.addObject("wrongParamenter", ex.toString());
        }

        return modelAndView;
    }

    @RequestMapping("/updateDepartment")
    public ModelAndView updateDeprtment(@RequestParam("departmentId") long id,
                                        @RequestParam("departmentName") String name){
        Department department = new Department(id, name, 0L);
        ModelAndView modelAndView = null;

        try{
            restTemplate.put(URL, department);
            modelAndView = new ModelAndView("redirect:/departments/");
        } catch(Exception ex){
            modelAndView = new ModelAndView("departmentPage", "departments", restTemplate.getForObject(URL, List.class));
            modelAndView.addObject("updatingError", true);
            modelAndView.addObject("wrongParamenter", ex.toString());
        }

        return modelAndView;
    }

    @RequestMapping("/removeDepartment")
    public ModelAndView removeDepartment(@RequestParam("departmentId") long depId){
        ModelAndView modelAndView = null;

        try {
            restTemplate.delete(URL + depId, depId);
            modelAndView = new ModelAndView("redirect:/departments/");
        } catch(Exception ex){
            modelAndView = new ModelAndView("departmentPage", "departments", restTemplate.getForObject(URL, List.class));

        }

        return modelAndView;
    }

}
