package com.location.location;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {
	
	//@Autowired
	//private MockMvc mockMvc;
	
//	@Test
//	public void shouldReturnDefaultMessage() throws Exception {
//		mockMvc.perform(get("/api/auth/login")).andDo(print()).andExpect(status().isOk());
//		
//	}
//	
//	@Test
//	public void userLoginTest() throws Exception{
//		mockMvc.perform(loginForm("/login").user("").password("")).andExpect(authenticated());
//	}
//	
//	@Test
//	public void userLoginFailedTest() throws Exception{
//		mockMvc.perform(loginForm("/login").user("").password("")).andExpect(unauthenticated());
//	}
//	
//	public void shouldReturnUserPage() throws Exception{
//		mockMvc.perform(get("/user")).andDo(print()).andExpect(status().isOk());
//	}
	

}
