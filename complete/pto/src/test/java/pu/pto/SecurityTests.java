package pu.pto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import pu.pto.config.web.SimpleSpringWebMvcConfig;
import pu.pto.domain.Employee;
import pu.pto.domain.EmployeeQueryInPort;
import pu.pto.domain.PTO;
import pu.pto.domain.PTOCommandInPort;
import pu.pto.domain.PTOQueryInPort;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SimpleSpringWebMvcConfig.class)
public class SecurityTests {

	@Autowired
	private PTOQueryInPort ptoQuery;

	@Autowired
	private EmployeeQueryInPort employeeQuery;

	@Autowired
	private PTOCommandInPort ptoCommand;

	@Test
	public void getAllEmployeesUnauthenticated() {
		assertThatThrownBy(() -> ptoQuery.getAllPTOs()).isInstanceOf(AuthenticationCredentialsNotFoundException.class);

	}

	@Test
	@WithMockUser(username = "mike", authorities = "ROLE_wrong")
	public void getAllEmployeesWithWrongRole() {
		assertThatThrownBy(() -> ptoQuery.getAllPTOs()).isInstanceOf(AccessDeniedException.class);
	}

	@Test
	@WithMockUser(username = "mike", authorities = "RIGHT_LIST_PTO")
	public void getAllEmployeesWithRight() {
		assertThat(ptoQuery.getAllPTOs()).isNotNull();
	}

	@Test
	@WithUserDetails("mike")
	public void getAllEmployeesUsingUserDetails() {
		assertThat(ptoQuery.getAllPTOs()).isNotNull();
	}

	@Test
	@WithMockPTOUser(username = "user1", role = "ROLE_Employee", right = "RIGHT_LIST_PTO")
	public void getAllEmployeesWithPTOUser() {
		assertThat(ptoQuery.getAllPTOs()).isNotNull();
		assertThat(employeeQuery.getCurrentActiveEmployee()).isNotNull();
		assertThat(employeeQuery.getCurrentActiveEmployee().getId()).isEqualTo("user1");
	}

	@Test
	@WithMockPTOUser(username = "user1", role = "ROLE_Employee", right = "RIGHT_CANCEL_PTO")
	public void cancelPTOfromDifferentEmployee() {
		PTO pto = PTO.of("na", Employee.of("user2", "na", "na", "na", "na", "na"), LocalDate.now(), LocalDate.now(),
				"na");
		assertThatThrownBy(() -> ptoCommand.cancelPTORequest(pto)).isInstanceOf(AccessDeniedException.class);
	}

	@Test
	@WithMockPTOUser(username = "user1", role = "ROLE_Employee", right = "RIGHT_CANCEL_PTO")
	public void cancelOwnPTO() {
		PTO pto = PTO.of("na", Employee.of("user1", "na", "na", "na", "na", "na"), LocalDate.now(), LocalDate.now(),
				"na");
		ptoCommand.cancelPTORequest(pto);
	}

}
