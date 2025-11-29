package dev.wisest.packaged;

/*-
 * #%L
 * "Learn Spring Boot by Examining 10+ Practical Applications" course materials
 * %%
 * Copyright (C) 2025 Juhan Aasaru and Wisest.dev
 * %%
 * The source code (including test code) in this repository is licensed under a
 * Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License.
 * %
 * Attribution-NonCommercial-NoDerivatives 4.0 International License is a standard
 * form license agreement that does not permit any commercial use or derivatives
 * of the original work. Under this license: you may only distribute a verbatim
 * copy of the work. If you remix, transform, or build upon the work in any way then
 * you are not allowed to publish nor distribute modified material.
 * You must give appropriate credit and provide a link to the license.
 * %
 * The full license terms are available from
 * <http://creativecommons.org/licenses/by-nc-nd/4.0/legalcode>
 * %
 * All the code (including tests) contained herein should be attributed as:
 * (Copyright) Juhan Aasaru https://Wisest.dev
 * #L%
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.FormLoginRequestBuilder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SecuredWebApplicationTests {
	@Autowired
	private MockMvc mockMvc;


    public void loginWithValidUser_userIsAuthenticated() throws Exception {
		FormLoginRequestBuilder login = formLogin()
                .user("regular")
			.password("password");

		mockMvc.perform(login)
                .andExpect(authenticated().withUsername("regular"));
	}


    public void loginWithNotExistingUser_userIsUnauthenticated() throws Exception {
		FormLoginRequestBuilder login = formLogin()
                .user("notExistingUser")
                .password("somePassword");

		mockMvc.perform(login)
			.andExpect(unauthenticated());
	}

    public void accessUnsecuredResource_okStatusCodeReturned() throws Exception {
		mockMvc.perform(get("/"))
			.andExpect(status().isOk());
	}

    public void accessSecuredResourceAsUnauthenticated_redirectsToLogin() throws Exception {
        mockMvc.perform(get("/members"))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrlPattern("**/login"));
	}

	//@WithMockUser
    public void accessSecuredResourceAsAuthenticated_okStatusCodeReturned() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/members"))
				.andExpect(status().isOk())
				.andReturn();

        assertThat(mvcResult.getResponse().getContentAsString()).contains("Hi user!");
	}
}
