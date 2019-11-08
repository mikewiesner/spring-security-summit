package pu.pto.config.web;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;

import pu.pto.domain.Employee;
import pu.pto.domain.EmployeeQueryInPort;
import pu.pto.domain.EmployeeRepository;
import pu.pto.domain.PTOUser;
import pu.pto.domain.UserRole;
import pu.pto.domain.UserRoleQueryInPort;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private EmployeeQueryInPort employeeQueryInPort;
	
	@Autowired
	private UserRoleQueryInPort userRoleQueryInPort;

	

	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.mvcMatchers("/PTO")//
					.hasRole("Employee")//
				.anyRequest()//
					.authenticated()//
					.and()//
				.csrf()//
					.disable()//
				.userDetailsService(ptoUserService())
				.formLogin();
	}

	
	
	
				
	@Bean
	public UserDetailsService ptoUserService() {
		
		return (username) -> {
			Employee employee = employeeQueryInPort.getEmployee(username);
			UserRole userRole = userRoleQueryInPort.findUserRoleByName(employee.getRole());
			HashSet<GrantedAuthority> ptoMappedAuthorities = new HashSet<GrantedAuthority>();
			ptoMappedAuthorities.add(new SimpleGrantedAuthority("ROLE_"+userRole.getId()));
			//ptoMappedAuthorities.addAll(AuthorityUtils.createAuthorityList(userRole.toRightsArray()));

			return new PTOUser(username,employee.getPassword(), ptoMappedAuthorities, employee);
		};
	}
	

}
