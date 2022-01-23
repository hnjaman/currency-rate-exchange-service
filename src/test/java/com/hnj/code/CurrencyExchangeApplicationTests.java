package com.hnj.code;

import com.hnj.code.model.Response.RegistrationResponse;
import com.hnj.code.model.request.UserRequest;
import com.hnj.code.service.JwtUserDetailsService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CurrencyExchangeApplicationTests {

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@Test
	public void userRegistration() {
		UserRequest userRequest = new UserRequest();
		userRequest.setEmail("ja@gmail.com");
		userRequest.setPass("1111");
		RegistrationResponse registrationResponse = userDetailsService.save(userRequest);
		Assert.assertEquals(true,registrationResponse.isStatus());
	}
}
