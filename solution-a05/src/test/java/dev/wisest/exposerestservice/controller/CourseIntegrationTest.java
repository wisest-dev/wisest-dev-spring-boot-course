package dev.wisest.exposerestservice.controller;

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

import dev.wisest.exposerestservice.model.Course;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@AutoConfigureTestRestTemplate
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CourseIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getSoftwareEngineerCourse() {
        Course course = this.restTemplate.getForObject(
                "http://localhost:{port}/courses/{courseId}",
                Course.class,
                port, "SUCCEED_AS_SOFTWARE_ENGINEER");

        Assertions.assertThat(course).isNotNull();
        Assertions.assertThat(course.getAuthor()).isNotNull();
        Assertions.assertThat(course.getAuthor().getName()).isEqualTo("John Smith");
    }

    @Test
    public void getCourseThatDoesNotExist() {

        ResponseEntity<String> courseResponseEntity = this.restTemplate.getForEntity(
                "http://localhost:{port}/courses/{courseId}",
                String.class,
                port, "NOT_EXISTING_COURSE");

        Assertions.assertThat(courseResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        Assertions.assertThat(courseResponseEntity.getBody()).isEqualTo("courseNotFoundHandler is here to tell you that: Could not find course NOT_EXISTING_COURSE");
    }

}


