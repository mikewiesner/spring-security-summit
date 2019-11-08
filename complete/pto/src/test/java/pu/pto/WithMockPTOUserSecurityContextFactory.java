package pu.pto;

import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import pu.pto.domain.Employee;
import pu.pto.domain.PTOUser;

public class WithMockPTOUserSecurityContextFactory implements WithSecurityContextFactory<WithMockPTOUser> {

	@Override
	public SecurityContext createSecurityContext(WithMockPTOUser annotation) {
		SecurityContext context = SecurityContextHolder.createEmptyContext();
		List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList(annotation.role(), annotation.right());
		Employee employee = Employee.of(annotation.username(), "not_used", "Test", "Test", "password_not_used", annotation.role());
		PTOUser ptoUser = new PTOUser(annotation.username(), "password_not_used", authorityList, employee);
		
		Authentication auth =
	            new UsernamePasswordAuthenticationToken(ptoUser, "password", ptoUser.getAuthorities());
		context.setAuthentication(auth);
		return context;
	}

}
