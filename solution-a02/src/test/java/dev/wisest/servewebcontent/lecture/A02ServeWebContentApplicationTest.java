
package dev.wisest.servewebcontent.lecture;

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

import dev.wisest.servewebcontent.GreetingController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(controllers = GreetingController.class)
public class A02ServeWebContentApplicationTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void homePage() throws Exception {
		mockMvc.perform(get("/index.html"))
				.andExpect(content().string(containsString("Get your personal greeting")));
	}

	@Test
	public void greeting() throws Exception {
		mockMvc.perform(post("/greeting"))
				.andExpect(content().string(containsString("Hello, World!")));
	}

	@Test
	public void greetingWithUser() throws Exception {
		mockMvc.perform(post("/greeting").param("name", "GREG"))
				.andExpect(content().string(containsString("Hello, GREG!")));
	}

	@Test
	public void greetingMinor() throws Exception {
		mockMvc.perform(post("/greeting")
						.param("name", "GREG")
						.param("dob", LocalDate.now().getYear()-3 + "-01-01"))
				.andExpect(content().string(containsString("years old which is less than 18.")));
	}

	@Test
	public void greetingGrownup() throws Exception {
		mockMvc.perform(post("/greeting")
						.param("name", "GREG")
						.param("dob", LocalDate.now().getYear()-30 + "-01-01"))
				.andExpect(content().string(containsString("years old which is more than 18.")));
	}

}
