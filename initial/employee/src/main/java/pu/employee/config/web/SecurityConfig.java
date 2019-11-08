package pu.employee.config.web;

import java.util.HashMap;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;


@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	

	
	@SuppressWarnings("serial")
	@Bean
	public UserDetailsService userDetailsService() {
		return (username) -> {
			HashMap<String, UserDetails> users = new HashMap<String, UserDetails>() {
				{
					put("mike", User.withUsername("mike")//
							.password("{bcrypt}$2a$10$j.X09oYOYg.o7EhIOR/mYO3YpiM/bgqyHvDFKbGfDjONzNfCUN8pq")//
							.authorities("ROLE_HR")//
							.build());
					put("bill", User.withUsername("bill")//
							.password("{bcrypt}$2a$10$j.X09oYOYg.o7EhIOR/mYO3YpiM/bgqyHvDFKbGfDjONzNfCUN8pq")//
							.authorities("ROLE_employee")//
							.build());
				}
			};
			
			return users.get(username);
		};
	}
	
	
	
	public static void main(String[] args) {
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		String encodePW = encoder.encode("test");
		System.out.println(encodePW);
	}
	

	


}
