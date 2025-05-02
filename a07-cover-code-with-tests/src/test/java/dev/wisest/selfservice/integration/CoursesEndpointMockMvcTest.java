package dev.wisest.selfservice.integration;

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

import jakarta.annotation.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CoursesEndpointMockMvcTest {

    @Resource
    private MockMvc mockMvc;

    @Resource
    ApplicationContext applicationContext;

    @BeforeEach
    public void logInformation() {
        System.out.println("There are " + applicationContext.getBeanDefinitionNames().length + " beans in context");
    }

    @Test
    public void shouldReturnListOfCourses() throws Exception {
        this.mockMvc
                .perform(get("/courses"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Ansible for Beginners")))
                .andExpect(content().string(containsString("Java for Complete Beginners")))
                .andExpect(content().string(containsString("Jenkins Bootcamp")))
                .andExpect(content().string(containsString("Test-Driven Development with Java")));
    }

}
