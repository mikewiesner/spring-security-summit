package pu.employee;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import pu.employee.config.web.SimpleSpringWebMvcConfig;
import pu.employee.domain.EmployeeQueryInPort;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=SimpleSpringWebMvcConfig.class)
public class SecurityTests {
	
	@Autowired
	private EmployeeQueryInPort employeeQueryInPort;
	
	@Test
	public void getAllEmployeesUnauthenticated() {
		assertThatThrownBy(() -> employeeQueryInPort.getAllEmployees())
			.isInstanceOf(AuthenticationCredentialsNotFoundException.class);
		
	}
	
	@Test
	@WithMockUser(username="mike", authorities="ROLE_Employee")
	public void getAllEmployeesWithWrongRole() {
		assertThatThrownBy(() -> employeeQueryInPort.getAllEmployees())
			.isInstanceOf(AccessDeniedException.class);
	}
	
	@Test
	@WithMockUser(username="mike", authorities="ROLE_HR")
	public void getAllEmployees() {
		assertThat(employeeQueryInPort.getAllEmployees()).
			isNotNull();
	}
	
}
