package pu.pto.domain;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;

@Getter
public class PTOUser extends User {
	
	private static final long serialVersionUID = -5242452500819547285L;
	private Employee employee;
	

	
	public PTOUser(String username, String password, Collection<GrantedAuthority> authorities, Employee employee) {
		super(username, password, authorities);
		
		
		this.employee = employee;
	}

}
