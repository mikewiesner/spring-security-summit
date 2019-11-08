package pu.pto;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import pu.pto.config.web.SimpleSpringWebMvcConfig;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=SimpleSpringWebMvcConfig.class)
public class SecurityWebTests {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;
    
    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    
    @Test
    public void loginRedirect() throws Exception {
    	mvc.perform(get("/PTO")).andExpect(status().is(302));
   }
    
    @Test
    @WithMockPTOUser(username="user1", role="ROLE_wrong", right="RIGHT_LIST_PTO")
    public void wrongRole() throws Exception {
    	mvc.perform(get("/PTO")).andExpect(status().isForbidden());
    }
    
    @Test
    @WithMockPTOUser(username="user1", role="ROLE_Employee", right="RIGHT_WRONG")
    public void wrongRight() throws Exception {
    	mvc.perform(get("/PTO")).andExpect(status().isForbidden());
    }
    
    @Test
    @WithMockPTOUser(username="user1", role="ROLE_Employee", right="RIGHT_LIST_PTO")
    public void accessGranted() throws Exception {
    	mvc.perform(get("/PTO")).andExpect(status().isOk());
    }
    
    
    @Test
    public void authenticationPasses() throws Exception {
    	mvc.perform(formLogin().user("mike").password("mike")).andExpect(redirectedUrl("/"));
    }
    
    @Test
    public void authenticationFailed() throws Exception {
    	mvc.perform(formLogin().user("mike").password("wrong")).andExpect(redirectedUrl("/login?error"));
    }
    


}
