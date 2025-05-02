package dev.wisest.validateforminput.signup;

/*-
 * #%L
 * Code accompanying video course "Learn Spring Boot by Examining 10+ Practical Applications"
 * %%
 * Copyright (C) 2025 Juhan Aasaru and Wisest.dev
 * %%
 * The source code (including test code) in this repository is licensed under a
 * Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International Licence.
 * 
 * Attribution-NonCommercial-NoDerivatives 4.0 International Licence is a standard
 * form licence agreement that does not permit any commercial use or derivatives
 * of the original work. Under this licence: you may only distribute a verbatim
 * copy of the work. If you remix, transform, or build upon the work in any way then
 * you are not allowed to publish nor distribute modified material.
 * You must give appropriate credit and provide a link to the licence.
 * 
 * The full licence terms are available from
 * <http://creativecommons.org/licenses/by-nc-nd/4.0/legalcode>
 * 
 * All the code (including tests) contained herein should be attributed as:
 * © Juhan Aasaru https://Wisest.dev
 * #L%
 */

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SignupTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void submitSignup_validForm_noErrors() throws Exception {

		MockHttpServletRequestBuilder validSignup = post("/signup")
				.param("username", "JohnSmith777")
				.param("springBootExpertise", "200")
				.param("dateOfBirth", "1999-12-13");

		mockMvc.perform(validSignup)
				.andExpect(model().hasNoErrors());
	}

	@Test
	public void getSignupForm_formWithRequiredFieldsIsDisplayed() throws Exception {
		MockHttpServletRequestBuilder newsletterSignupPage = get("/signup");

		mockMvc.perform(newsletterSignupPage)
				.andExpect(content().string(containsStringIgnoringCase("Username:")))
				.andExpect(content().string(containsStringIgnoringCase("Ready to pay per course ($):")))
				.andExpect(content().string(containsStringIgnoringCase("Date of birth:")));
	}

	@Test
	public void submitSignup_nothingPosted_hasErrors() throws Exception {
		MockHttpServletRequestBuilder newsletterOrder = post("/signup");

		mockMvc.perform(newsletterOrder)
				.andExpect(model().hasErrors())
				.andExpect(model().errorCount(2));
	}

	@Test
	public void submitSignup_birthDateInTheFuture_hasError() throws Exception {
		MockHttpServletRequestBuilder invalidSignup = post("/signup")
				.param("username", "JohnSmith777")
				.param("springBootExpertise", "200")
				.param("dateOfBirth", "2055-12-13");

		mockMvc.perform(invalidSignup)
				.andExpect(model().hasErrors())
				.andExpect(model().errorCount(1))
				.andExpect(content().string(containsStringIgnoringCase("must be a past date")));
	}

	@Test
	public void submitSignup_birthDateInWrongFormat_hasError() throws Exception {
		MockHttpServletRequestBuilder invalidSignup = post("/signup")
				.param("username", "JohnSmith777")
				.param("springBootExpertise", "200")
				.param("dateOfBirth", "12/12/1997");

		mockMvc.perform(invalidSignup)
				.andExpect(model().hasErrors())
				.andExpect(model().errorCount(1))
				.andExpect(content().string(containsStringIgnoringCase("Invalid birthdate format. Please use yyyy-mm-dd.")));
	}

	@Test
	public void submitSignup_birthDateBeforeYear1900_customError() throws Exception {
		MockHttpServletRequestBuilder invalidSignup = post("/signup")
				.param("username", "JohnSmith777")
				.param("springBootExpertise", "200")
				.param("dateOfBirth", "1899-12-13");

		mockMvc.perform(invalidSignup)
				.andExpect(model().hasErrors())
				.andExpect(model().errorCount(1))
				.andExpect(content().string(containsStringIgnoringCase("You can not be that old! The earliest accepted birthdate is 1900-01-01")));
	}

	@Test
	public void submitSignup_tooManyDigits_failure() throws Exception {
		MockHttpServletRequestBuilder invalidSignup = post("/signup")
				.param("username", "JohnSmith777")
				.param("springBootExpertise", "1234.123")
				.param("dateOfBirth", "1955-12-13");

		mockMvc.perform(invalidSignup)
			.andExpect(model().hasErrors())
			.andExpect(model().errorCount(1))
			.andExpect(content().string(containsStringIgnoringCase("numeric value out of bounds")));
	}

	@Test
	public void checkPersonInfoWhenNameTooShortThenFailure() throws Exception {
		MockHttpServletRequestBuilder newsletterOrder = post("/signup")
				.param("name", "R")
				.param("age", "20");

		mockMvc.perform(newsletterOrder)
			.andExpect(model().hasErrors());
	}

	@Test
	public void checkPersonInfoWhenAgeMissingThenFailure() throws Exception {
		MockHttpServletRequestBuilder newsletterOrder = post("/signup")
				.param("name", "Rob");

		mockMvc.perform(newsletterOrder)
			.andExpect(model().hasErrors());
	}

	@Test
	public void checkPersonInfoWhenAgeTooYoungThenFailure() throws Exception {
		MockHttpServletRequestBuilder newsletterOrder = post("/signup")
				.param("age", "1")
				.param("name", "Rob");

		mockMvc.perform(newsletterOrder)
			.andExpect(model().hasErrors());
	}






	@Test
	public void formWithRequiredFieldsIsDisplayed() throws Exception { // AC01
		MockHttpServletRequestBuilder newsletterSignupPage = get("/newsletter");

		mockMvc.perform(newsletterSignupPage)
				.andExpect(content().string(containsStringIgnoringCase("Name")))
				.andExpect(content().string(containsStringIgnoringCase("Age")))
				.andExpect(content().string(containsStringIgnoringCase("Email")));
	}

	@Test
	public void checkNewsletterForm_noEmailSubmitted_hasError() throws Exception {
		MockHttpServletRequestBuilder newsletterOrder = post("/newsletter")
				.param("name", "John Smith")
				.param("age", "54");

		mockMvc.perform(newsletterOrder)
				.andExpect(model().hasErrors());
	}

	@Test
	public void checkNewsletterForm_incorrectEmailSubmitted_hasError() throws Exception {
		MockHttpServletRequestBuilder invalidOrder = post("/newsletter")
				.param("name", "John Smith")
				.param("age", "54")
				.param("email", "London 1");

		mockMvc.perform(invalidOrder)
				.andExpect(model().hasErrors());
	}

	@Test
	public void checkNewsletterForm_nameMissingName_failure() throws Exception {
		MockHttpServletRequestBuilder newsletterOrder = post("/newsletter")
				.param("email", "test@example.org")
				.param("age", "100");

		mockMvc.perform(newsletterOrder)
				.andExpect(model().hasErrors());
	}

	/*
	@Test
	public void checkPersonInfoWhenNameTooShortThenFailure() throws Exception {
		MockHttpServletRequestBuilder newsletterOrder = post("/newsletter")
				.param("name", "R")
				.param("age", "20");

		mockMvc.perform(newsletterOrder)
				.andExpect(model().hasErrors());
	}

	@Test
	public void checkPersonInfoWhenAgeMissingThenFailure() throws Exception {
		MockHttpServletRequestBuilder newsletterOrder = post("/newsletter")
				.param("name", "Rob");

		mockMvc.perform(newsletterOrder)
				.andExpect(model().hasErrors());
	}

	@Test
	public void checkPersonInfoWhenAgeTooYoungThenFailure() throws Exception {
		MockHttpServletRequestBuilder newsletterOrder = post("/newsletter")
				.param("age", "1")
				.param("name", "Rob");

		mockMvc.perform(newsletterOrder)
				.andExpect(model().hasErrors());
	}

	@Test
	public void submitNewsletterOrder_validForm_noErrors() throws Exception {

		MockHttpServletRequestBuilder validNewsletterOrder = post("/newsletter")
				.param("name", "John Smith")
				.param("age", "54")
				.param("email", "London 1");

		mockMvc.perform(validNewsletterOrder)
				.andExpect(model().hasNoErrors());
	}
	*/

}
