package by.task.web;

import by.task.model.Department;
import by.task.model.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by simpson on 17.2.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:/test-context.xml")
public class EmployeeWebControllerTest {
    private static String URL = "http://localhost:8080/rest/restEmployee/";

    private MockMvc mockMvc;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    protected WebApplicationContext wac;
    @Autowired
    protected MockHttpServletRequest mockHttpServletRequest;
    @Autowired
    private MockHttpSession mockHttpSession;

    @Before
    public void setUp() {
        this.mockMvc = webAppContextSetup(this.wac).build();
    }

    @After
    public void clean() {
        reset(restTemplate);
    }

    private static final String DEPARTMENT_ROOT = "/employees";

    @Test
    public void testGetAll() throws Exception {
        List<Employee> list = new ArrayList<>();

        list.add(new Employee(1L, "EMPLOYEE TEST", new Date(1993, 10, 10), 100L, 2L));
        expect(restTemplate.getForObject(URL, List.class)).andReturn(list);
        replay(restTemplate);

        this.mockMvc.perform(get("/employees/")
                .accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(view().name("employeePage"))
                .andReturn();

        verify(restTemplate);
    }

    @Test
    public void testAdd() throws Exception {
        Employee employee = new Employee(0L, "EMPLOYEE TEST", new Date(1993, 10, 10), 100L, 2L);
        expect(restTemplate.postForObject(URL, employee, long.class)).andReturn(0L);
        replay(restTemplate);
        ObjectMapper mapper = new ObjectMapper();

        this.mockMvc.perform(post("/employees/addEmployee/")
                .contentType(MediaType.APPLICATION_JSON)
                // .content(mapper.writeValueAsString(department))
                .accept("application/json")
                .param("fullName", "EMPLOYEE TEST")
                .param("date", "3893-11-10")
                .param("salary", "100")
                .param("departmentId", "2"))
                .andExpect(status().isMovedTemporarily())
                .andExpect(view().name("redirect:/employees/"))
                .andExpect(redirectedUrl("/employees/"))
                .andReturn();

        verify(restTemplate);
    }

    @Test
    public void testUpdate() throws Exception {
        Employee employee = new Employee(1L, "EMPLOYEE TEST", new Date(1993, 10, 10), 100L, 2L);
        restTemplate.put(URL, employee);
        expectLastCall().once();
        replay(restTemplate);
        ObjectMapper mapper = new ObjectMapper();

        this.mockMvc.perform(put("/employees/updateEmployee/")
                .contentType(MediaType.APPLICATION_JSON)
                // .content(mapper.writeValueAsString(department))
                .accept("application/json")
                .param("employeeId", "1")
                .param("fullName", "EMPLOYEE TEST")
                .param("date", "3893-11-10")
                .param("salary", "100")
                .param("departmentId", "2"))
                .andExpect(status().isMovedTemporarily())
                .andExpect(view().name("redirect:/employees/"))
                .andExpect(redirectedUrl("/employees/"))
                .andReturn();

        verify(restTemplate);
    }

    @Test
    public void testDelete() throws Exception {
        Employee employee = new Employee(1L, "EMPLOYEE TEST", new Date(1993, 10, 10), 100L, 2L);
        restTemplate.delete(URL + 1L, 1L);
        expectLastCall().once();
        replay(restTemplate);
        ObjectMapper mapper = new ObjectMapper();

        this.mockMvc.perform(delete("/employees/removeEmployee/")
                .contentType(MediaType.APPLICATION_JSON)
                // .content(mapper.writeValueAsString(department))
                .accept("application/json")
                .param("employeeId", "1"))
                .andExpect(status().isMovedTemporarily())
                .andExpect(view().name("redirect:/employees/"))
                .andExpect(redirectedUrl("/employees/"))
                .andReturn();

        verify(restTemplate);
    }

    @Test
    public void testEmployeesOfDepartment() throws Exception {
        List<Employee> list = new ArrayList<>();

        list.add(new Employee(1L, "EMPLOYEE TEST", new Date(1993, 10, 10), 100L, 2L));
        expect(restTemplate.getForObject(URL + "/employeesByDepartmentId/" + 1, List.class)).andReturn(list);
        replay(restTemplate);

        this.mockMvc.perform(get("/employees/employeesOfDepartment")
                .accept("application/json")
                .param("departmentId", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("employeePage"))
                .andReturn();

        verify(restTemplate);
    }
}