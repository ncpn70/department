package by.task.rest;

import by.task.model.Department;
import by.task.service.DepartmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring-rest-mock-test.xml"})
public class DepartmentRestControllerMockTest {

    private MockMvc mockMvc;

    @Resource
    private DepartmentRestController controller;

    @Autowired
    private DepartmentService departmentService;

    private static final String DEPARTMENT_REST_ROOT = "/restDepartment";

    @Before
    public void setUp() {
        this.mockMvc = standaloneSetup(controller)
                .setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
    }

    @After
    public void clean() {
        reset(departmentService);
    }

    @Test
    public void testAdd() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        Department department = new Department(2, "REST TEST DEPARTMENT", 1);
        expect(departmentService.add(department)).andReturn(2L);
        replay(departmentService);

        String departmentJSON = mapper.writeValueAsString(department);

        mockMvc.perform(
                post(DEPARTMENT_REST_ROOT)
                .content(departmentJSON)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isCreated());

        verify(departmentService);

    }

    @Test
    public void testUpdate() throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        Department department = new Department(2, "REST TEST DEPARTMENT", 1);

        departmentService.update(department);
        expectLastCall().once();

        replay(departmentService);

        String starJson = objectMapper.writeValueAsString(department);

        this.mockMvc.perform(
                put(DEPARTMENT_REST_ROOT)
                        .content(starJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isAccepted());

        verify(departmentService);
    }

    @Test
    public void testRemove() throws Exception{
        departmentService.remove(2L);
        expectLastCall().once();

        replay(departmentService);

        this.mockMvc.perform(
                delete(DEPARTMENT_REST_ROOT + "/" + 2L))
                .andDo(print())
                .andExpect(status().isAccepted());

        verify(departmentService);
    }

    @Test
    public void testGetById() throws Exception{
        Department department = new Department(2, "REST TEST DEPARTMENT", 1);
        String expextedStr = "{\"departmentId\":2,\"departmentName\":\"REST TEST DEPARTMENT\",\"averageSalary\":1}";
        expect(departmentService.getById(3))
                .andReturn(department);
        replay(departmentService);

        mockMvc.perform(get(DEPARTMENT_REST_ROOT + "/departmentById/" + 3)
            .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(expextedStr));

        verify(departmentService);
    }

    @Test
    public void testGetAllGalaxies() throws Exception{
        List<Department> departments = new ArrayList<>();
        departments.add(new Department(2, "REST TEST DEPARTMENT", 1));
        departments.add(new Department(3, "TESSSSST", 1));


        expect(departmentService.getAll()).andReturn(departments);
        replay(departmentService);

        mockMvc.perform(get(DEPARTMENT_REST_ROOT)
                .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .string("[{\"departmentId\":2,\"departmentName\":\"REST TEST DEPARTMENT\",\"averageSalary\":1},{\"departmentId\":3,\"departmentName\":\"TESSSSST\",\"averageSalary\":1}]"));

        verify(departmentService);
    }
}