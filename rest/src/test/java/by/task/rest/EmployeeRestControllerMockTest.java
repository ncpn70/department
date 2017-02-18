package by.task.rest;

import by.task.model.Department;
import by.task.model.Employee;
import by.task.service.DepartmentService;
import by.task.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.easymock.EasyMock.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring-rest-mock-test.xml"})
public class EmployeeRestControllerMockTest {
    private static final Logger LOGGER = LogManager.getLogger(EmployeeRestControllerMockTest.class);

    private MockMvc mockMvc;

    @Resource
    private EmployeeRestController controller;

    @Autowired
    private EmployeeService employeeService;

    private static String EMPLOEYY_REST_ROOT = "/restEmployee";

    @Before
    public void setUp() {
        this.mockMvc = standaloneSetup(controller)
                .setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
    }

    @After
    public void clean() {
        reset(employeeService);
    }

    @Test
    //@Ignore
    public void testAddEmployee() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        Employee employee1 = new Employee(0L, "EMPLOYEE TEST", new Date(1993, 10, 10), 100L, 2L);
        expect(employeeService.add(employee1)).andReturn(2L);
        replay(employeeService);
        String employeeJSON = mapper.writeValueAsString(employee1);

        LOGGER.debug(employeeJSON);

        this.mockMvc.perform(
                post(EMPLOEYY_REST_ROOT)
                        .content(employeeJSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isCreated());

        verify(employeeService);
    }

    @Test
    public void testUpdateStar() throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        Employee employee1 = new Employee(0L, "EMPLOYEE TEST", new Date(1993, 10, 10), 100L, 2L);

        employeeService.update(employee1);
        expectLastCall().once();

        replay(employeeService);

        String starJson = objectMapper.writeValueAsString(employee1);

        this.mockMvc.perform(
                put(EMPLOEYY_REST_ROOT)
                        .content(starJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isAccepted());

        verify(employeeService);
    }

    @Test
    public void testRemove() throws Exception{
        employeeService.remove(3);
        expectLastCall().once();

        replay(employeeService);

        this.mockMvc.perform(
                delete(EMPLOEYY_REST_ROOT + "/" + 3))
                .andDo(print())
                .andExpect(status().isAccepted());

        verify(employeeService);
    }

    @Test
    public void testGetEmployeeById() throws Exception {

        expect(employeeService.getById(1))
                .andReturn(new Employee(0L, "EMPLOYEE TEST", new Date(1993, 10, 10), 100L, 2L));
        replay(employeeService);

        mockMvc.perform(get(EMPLOEYY_REST_ROOT + "/employeeById/" + 1)
                .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("{\"employeeId\":0,\"fullName\":\"EMPLOYEE TEST\",\"birthDate\":\"3893-11-10\",\"salary\":100,\"departmentId\":2}"));

        verify(employeeService);
    }

    @Test
    public void testGetEmplpoyeeByDepartmentId() throws Exception {

        List<Employee> list = new ArrayList<>();
        list.add(new Employee(1L, "EMPLOYEE TEST", new Date(1993, 10, 10), 100L, 2L));
        expect(employeeService.getByDepartmentId(1))
                .andReturn(list);
        replay(employeeService);

        mockMvc.perform(get(EMPLOEYY_REST_ROOT + "/employeesByDepartmentId/" + 1)
                .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"employeeId\":1,\"fullName\":\"EMPLOYEE TEST\",\"birthDate\":\"3893-11-10\",\"salary\":100,\"departmentId\":2}]"));

        verify(employeeService);
    }

    @Test
    public void testGetAll() throws Exception{
        List<Employee> list = new ArrayList<>();
        list.add(new Employee(1L, "EMPLOYEE TEST", new Date(1993, 10, 10), 100L, 2L));

        expect(employeeService.getAll()).andReturn(list);
        replay(employeeService);

        mockMvc.perform(get(EMPLOEYY_REST_ROOT)
                .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .string("[{\"employeeId\":1,\"fullName\":\"EMPLOYEE TEST\",\"birthDate\":\"3893-11-10\",\"salary\":100,\"departmentId\":2}]"));

        verify(employeeService);
    }

}
