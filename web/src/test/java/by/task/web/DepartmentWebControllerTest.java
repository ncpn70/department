package by.task.web;

import by.task.model.Department;
import by.task.model.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.Resource;
import static org.easymock.EasyMock.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by simpson on 13.2.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:/test-context.xml")
public class DepartmentWebControllerTest {
    private static String URL = "http://localhost:8080/rest/restDepartment/";

    private MockMvc mockMvc;
  //  private MockMvc mockMvcRt;

    @Autowired
    private RestTemplate restTemplate;

   // @Resource
  //  private DepartmentWebController departmentWebController;

    @Autowired
    protected WebApplicationContext wac;
    @Autowired
    protected MockHttpServletRequest mockHttpServletRequest;
    @Autowired
    private MockHttpSession mockHttpSession;

    @Before
    public void setUp() {
        this.mockMvc = webAppContextSetup(this.wac).build();
   //     this.mockMvcRt = standaloneSetup(departmentWebController).build();
    }

    @After
    public void clean() {
        reset(restTemplate);
    }

    private static final String DEPARTMENT_ROOT = "/departments";

    @Test
    public void testGetAll() throws Exception {
        List<Department> list = new ArrayList<>();

        list.add(new Department("NAME", 0));
        expect(restTemplate.getForObject(URL, List.class)).andReturn(list);
        replay(restTemplate);

        this.mockMvc.perform(get("/departments/")
                .accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(view().name("departmentPage"))
                .andReturn();

        verify(restTemplate);
    }

    @Test
    public void testAdd() throws Exception {
        Department department = new Department("TTESTSTS", 0L);
        expect(restTemplate.postForObject(URL, department, long.class)).andReturn(0L);
        replay(restTemplate);
        ObjectMapper mapper = new ObjectMapper();

        this.mockMvc.perform(post("/departments/addDepartment/")
                .contentType(MediaType.APPLICATION_JSON)
               // .content(mapper.writeValueAsString(department))
                .accept("application/json")
                .param("name", "TTESTSTS"))
                .andExpect(status().isMovedTemporarily())
                .andExpect(view().name("redirect:/departments/"))
                .andExpect(redirectedUrl("/departments/"))
                .andReturn();

        verify(restTemplate);
    }

    @Test
    public void testUpdate() throws Exception {
        Department department = new Department(1,"new Dep Name", 0L);
        restTemplate.put(URL,department);
        expectLastCall().once();

        replay(restTemplate);
        ObjectMapper mapper = new ObjectMapper();

        this.mockMvc.perform(put("/departments/updateDepartment/")
                .contentType(MediaType.APPLICATION_JSON)
               // .content(mapper.writeValueAsString(department))
                .accept("application/json")
                .param("departmentId", "1")
                .param("departmentName", "new Dep Name"))
                .andExpect(status().isMovedTemporarily())
                .andExpect(view().name("redirect:/departments/"))
                .andExpect(redirectedUrl("/departments/"))
                .andReturn();

        verify(restTemplate);
    }

    @Test
    public void testDelete() throws Exception {
        restTemplate.delete(URL + 1, 1L);
        expectLastCall().once();

        replay(restTemplate);
        ObjectMapper mapper = new ObjectMapper();

        this.mockMvc.perform(put("/departments/removeDepartment/")
                .contentType(MediaType.APPLICATION_JSON)
                // .content(mapper.writeValueAsString(department))
                .accept("application/json")
                .param("departmentId", "1")
               // .param("departmentName", "new Dep Name")
                )
                .andExpect(status().isMovedTemporarily())
                .andExpect(view().name("redirect:/departments/"))
                .andExpect(redirectedUrl("/departments/"))
                .andReturn();

        verify(restTemplate);
    }
}
