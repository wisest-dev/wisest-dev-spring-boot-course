package dev.wisest.selfservice.integration;

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

import jakarta.annotation.Resource;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.web.client.RestClient;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CoursesEndpointIntegrationTest {

	@LocalServerPort
	private int port;

	private RestClient restClient;

	@Resource
	ApplicationContext applicationContext;

    @BeforeEach
	public void setUp() {
        this.restClient = RestClient.builder().build();

		System.out.println("There are " + applicationContext.getBeanDefinitionNames().length + " beans in context");
	}

	@Test
	public void getCourses_shouldReturnListOfCourses() {

        String responseString = restClient.get()
                .uri("http://localhost:" + port + "/courses")
                .retrieve()
                .body(String.class);


		Assertions.assertThat(responseString)
				.contains("\"Ansible for Beginners\",\"Java for Complete Beginners\",\"Jenkins Bootcamp\",\"Test-Driven Development with Java\"");
	}

}
