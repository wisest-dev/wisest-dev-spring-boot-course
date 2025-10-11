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
import dev.wisest.exposerestservice.model.Enrollment;
import dev.wisest.exposerestservice.model.Person;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.test.LocalServerPort;
import org.springframework.boot.web.server.test.client.TestRestTemplate;
import org.springframework.http.*;

import java.time.LocalDate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UpdateEnrollmentIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void updateEnrollment_courseIdInPayloadDoesNotMatchCourseIdInPath() {

        String juniorMoreInterviewsCourseId = "JUNIOR_MORE_INTERVIEWS";
        Course moreInterviewsCourse = new Course(juniorMoreInterviewsCourseId);

        Person student = new Person(1L);

        Enrollment enrollment = new Enrollment(student, moreInterviewsCourse, LocalDate.of(2020, 2, 14));


        String postUrl = "http://localhost:{port}/courses/{courseId}/enrollments";

        ResponseEntity<String> responseEntity = this.restTemplate.exchange(
                "http://localhost:{port}/courses/{courseId}/enrollments",
                HttpMethod.PUT,
                new HttpEntity<>(enrollment),
                String.class,
                port,
                "SOME_OTHER_COURSE_ID" // This is intentionally wrong
        );

        HttpStatusCode statusCode = responseEntity.getStatusCode();

        Assertions.assertThat(statusCode).isEqualTo(HttpStatus.BAD_REQUEST);

        Assertions.assertThat(responseEntity.getBody())
                .isEqualTo("Validation failed: The courseId in path and body must match");
    }


    @Test
    public void updateEnrollment_studentsDoNotMatch_conflict() {
        Enrollment existingEnrollment = addEnrollment(1L, "JUNIOR_MORE_INTERVIEWS", LocalDate.of(2000, 3, 15));
        existingEnrollment.getStudent().setName("Student has changed name");

        ResponseEntity<String> responseEntity = this.restTemplate.exchange(
                "http://localhost:{port}/courses/{courseId}/enrollments",
                HttpMethod.PUT,
                new HttpEntity<>(existingEnrollment),
                String.class,
                port,
                existingEnrollment.getCourse().getCourseId()
        );

        Assertions.assertThat(responseEntity.getStatusCode().value())
                .isEqualTo(HttpStatus.CONFLICT.value());

        Assertions.assertThat(responseEntity.getBody())
                .isEqualTo("Cannot change student name");;

    }

    @Test
    public void updateEnrollment_newEnrollment_created() {
        String succeedAsSoftwareEngineerCourse = "SUCCEED_AS_SOFTWARE_ENGINEER";
        Course moreInterviewsCourse = new Course(succeedAsSoftwareEngineerCourse);

        Person student = new Person(1L);

        Enrollment enrollment = new Enrollment(
                student, moreInterviewsCourse, LocalDate.of(2025, 1, 13));


        ResponseEntity<String> responseEntity = this.restTemplate.exchange(
                "http://localhost:{port}/courses/{courseId}/enrollments",
                HttpMethod.PUT,
                new HttpEntity<>(enrollment),
                String.class,
                port, succeedAsSoftwareEngineerCourse
        );

        Assertions.assertThat(responseEntity.getStatusCode().value())
                .isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    public void updateEnrollment_existingEnrollment_enrollmentDateUpdated() {

        Enrollment existingEnrollment = addEnrollment(1L, "SUCCEED_AS_SOFTWARE_ENGINEER", LocalDate.of(2000, 3, 15));


        ResponseEntity<String> responseEntity = this.restTemplate.exchange(
                "http://localhost:{port}/courses/{courseId}/enrollments",
                HttpMethod.PUT,
                new HttpEntity<>(existingEnrollment),
                String.class,
                port, existingEnrollment.getCourse().getCourseId()
        );

        Assertions.assertThat(responseEntity.getStatusCode().value())
                .isEqualTo(HttpStatus.OK.value());
    }

    private Enrollment addEnrollment(long studentPersonId, String courseId, LocalDate enrollmentDate) {
        Course moreInterviewsCourse = new Course(courseId);

        Person student = new Person(studentPersonId);

        Enrollment enrollment = new Enrollment(student, moreInterviewsCourse, enrollmentDate);

        String postUrl = "http://localhost:{port}/courses/{courseId}/enrollments";

        ResponseEntity<Enrollment> responseEntity = this.restTemplate.postForEntity(
                postUrl,
                enrollment,
                Enrollment.class,
                port, courseId, enrollment
        );
        return responseEntity.getBody();
    }

}


