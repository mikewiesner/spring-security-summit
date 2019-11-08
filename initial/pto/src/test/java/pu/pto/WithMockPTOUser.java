package pu.pto;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.security.test.context.support.WithSecurityContext;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockPTOUserSecurityContextFactory.class)
public @interface WithMockPTOUser {
	
    String username() default "mike";

    String role() default "Employee";
    
    String right();

}
