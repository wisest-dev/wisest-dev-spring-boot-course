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
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.UUID;

@AutoConfigureTestRestTemplate
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EnrollmentIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void addEnrollmentAndPullBack() {
        String juniorMoreInterviewsCourseId = "JUNIOR_MORE_INTERVIEWS";
        Course moreInterviewsCourse = new Course(juniorMoreInterviewsCourseId);

        ResponseEntity<Person[]> response = this.restTemplate.getForEntity(
                "http://localhost:{port}/persons?name={name}",
                Person[].class,
                port, "John Smith");

        Assertions.assertThat(response.getBody()).isNotNull();
        Person student = response.getBody()[0];

        Enrollment enrollment = new Enrollment(student, moreInterviewsCourse, LocalDate.of(2025, 1, 13));


        String postUrl = "http://localhost:{port}/courses/{courseId}/enrollments";

        ResponseEntity<Enrollment> responseEntity = this.restTemplate.postForEntity(
                postUrl,
                enrollment,
                Enrollment.class,
                port, juniorMoreInterviewsCourseId, enrollment
        );

        HttpStatusCode statusCode = responseEntity.getStatusCode();

        Assertions.assertThat(statusCode.value()).isEqualTo(HttpStatus.CREATED.value());

        Assertions.assertThat(responseEntity.getBody()).isNotNull();

        UUID createdUuid = responseEntity.getBody().getUuid();


        String url = "http://localhost:{port}/courses/{courseId}/enrollments/{enrollmentUuid}";

        ResponseEntity<Enrollment> forEntity = this.restTemplate.getForEntity(
                url,
                Enrollment.class,
                port, juniorMoreInterviewsCourseId, createdUuid
        );
        Enrollment pulledEnrollment = forEntity.getBody();
        Assertions.assertThat(pulledEnrollment).isNotNull();
        Assertions.assertThat(pulledEnrollment.getEnrollmentDate()).isEqualTo(LocalDate.of(2025, 1, 13));
    }

    @Test
    public void addNonValidEnrollmentWithEnrollmentDateInTheFuture() {

        String juniorMoreInterviewsCourseId = "JUNIOR_MORE_INTERVIEWS";
        Course moreInterviewsCourse = new Course(juniorMoreInterviewsCourseId);

        Person student = new Person(1L);

        Enrollment enrollment = new Enrollment(student, moreInterviewsCourse, LocalDate.of(2122, 1, 13));


        String postUrl = "http://localhost:{port}/courses/{courseId}/enrollments";

        ResponseEntity<String> responseEntity = this.restTemplate.postForEntity(
                postUrl,
                enrollment,
                String.class,
                port, juniorMoreInterviewsCourseId, enrollment
        );

        HttpStatusCode statusCode = responseEntity.getStatusCode();

        Assertions.assertThat(statusCode).isEqualTo(HttpStatus.BAD_REQUEST);

        Assertions.assertThat(responseEntity.getBody()).isEqualTo("{\"enrollmentDate\":\"must be a date in the past or in the present\"}");
    }

    @Test
    public void addNonValidEnrollmentWithEnrollmentIdInUrlNotMatchingTheEnrollmentIdInThePayload() {

        String juniorMoreInterviewsCourseId = "JUNIOR_MORE_INTERVIEWS";
        Course moreInterviewsCourse = new Course(juniorMoreInterviewsCourseId);

        Person student = new Person(1L);

        Enrollment enrollment = new Enrollment(student, moreInterviewsCourse, LocalDate.of(2020, 2, 14));


        String postUrl = "http://localhost:{port}/courses/{courseId}/enrollments";

        ResponseEntity<String> responseEntity = this.restTemplate.postForEntity(
                postUrl,
                enrollment,
                String.class,
                port, "BEGINNER_SPRING_BOOT", enrollment
        );

        HttpStatusCode statusCode = responseEntity.getStatusCode();

        Assertions.assertThat(statusCode).isEqualTo(HttpStatus.BAD_REQUEST);

        Assertions.assertThat(responseEntity.getBody())
                .isEqualTo("Validation failed: The courseId in path and body must match");
    }

    @Test
    public void deleteEnrollment() {

        String juniorMoreInterviewsCourseId = "JUNIOR_MORE_INTERVIEWS";
        Course moreInterviewsCourse = new Course(juniorMoreInterviewsCourseId);

        Person student = new Person(1L);

        Enrollment enrollment = new Enrollment(student, moreInterviewsCourse, LocalDate.of(2000, 3, 15));


        String postUrl = "http://localhost:{port}/courses/{courseId}/enrollments";

        ResponseEntity<Enrollment> responseEntity = this.restTemplate.postForEntity(
                postUrl,
                enrollment,
                Enrollment.class,
                port, juniorMoreInterviewsCourseId, enrollment
        );

        HttpStatusCode statusCode = responseEntity.getStatusCode();

        Assertions.assertThat(statusCode).isEqualTo(HttpStatus.CREATED);

        Enrollment createdEnrollment = responseEntity.getBody();
        Assertions.assertThat(createdEnrollment).isNotNull();

        String deleteUrl = "http://localhost:{port}/courses/{courseId}/enrollments/{enrollmentId}";
        ResponseEntity<String> result = this.restTemplate.exchange(deleteUrl, HttpMethod.DELETE, null, String.class,
                port, juniorMoreInterviewsCourseId, createdEnrollment.getUuid());

        Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);


        ResponseEntity<String> response = this.restTemplate.getForEntity(
                deleteUrl,
                String.class,
                port, juniorMoreInterviewsCourseId, createdEnrollment.getUuid()
        );
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        Assertions.assertThat(response.getBody()).startsWith("enrollmentNotFoundHandler is here to tell you that: Could not find enrollment ");
    }


}


