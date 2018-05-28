package TechSandbox;

import TechSandbox.Service.EmployeeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc

public class HelloWorldSiApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Before
	public void cleanEmployees() {
		employeeRepository.deleteAll();
	}

	@Test
	public void contextLoads() {
	}

	@Test
	public void shouldReturnRepositoryIndex() throws Exception {

		mockMvc.perform(get("/api")).andDo(print()).andExpect(status().isOk()).andExpect(
				jsonPath("$._links.employees").exists());
	}


	@Test
	public void shouldCreateEntity() throws Exception {

		String myContent = "{\"name\": \"Ashok Natesan\", \n" +
				"\"userid\": \"anatesan\",\n" +
				"\"department\": \"/api/departments/1\"\n" +
				"}}";
		System.out.println(myContent);

		mockMvc.perform(post("/api/employees").content(
				myContent)).andExpect(
				status().isCreated()).andExpect(
				header().string("Location", containsString("employees/")));
	}


}
