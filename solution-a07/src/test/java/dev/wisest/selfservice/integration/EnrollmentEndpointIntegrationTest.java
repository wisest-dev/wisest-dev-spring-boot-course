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

import dev.wisest.selfservice.model.CodingCourseEnrollment;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class EnrollmentEndpointIntegrationTest {

	@LocalServerPort
	private int port;

	@Test
	public void postEnrollment_enrollmentIsInThePast_enrollmentIsCreated() {

        RestClient restClient = RestClient.builder().build();

        CodingCourseEnrollment newEnrollment = new CodingCourseEnrollment(
                "JAVA",
                "John Smith",
                LocalDate.of(2000, 1, 1));


        ResponseEntity<Void> bodilessEntity = restClient.post()
                .uri("http://localhost:" + port + "/enrollments")
                .body(newEnrollment)
                .retrieve()
                .toBodilessEntity();

        Assertions.assertThat(bodilessEntity.getStatusCode()).isEqualTo(CREATED);

	}

    @Test
    public void postEnrollment_enrollmentIsInTheFuture_badRequestReturned_usingTryCatch() {

        RestClient restClient = RestClient.builder().build();

        CodingCourseEnrollment enrollmentInFuture = new CodingCourseEnrollment(
                "JAVA",
                "John Smith",
                LocalDate.of(2222, 1, 1));


        Exception catchedException = null;

        try {
            restClient.post()
                    .uri("http://localhost:" + port + "/enrollments")
                    .body(enrollmentInFuture)
                    .retrieve()
                    .toBodilessEntity();
        }
        catch (Exception e) {
            catchedException = e;
        }
        Assertions.assertThat(catchedException).hasMessageContaining("400 Bad Request");

    }


	@Test
	public void postEnrollment_enrollmentIsInTheFuture_badRequestReturned_usingAssertThatThrownBy() {

        RestClient restClient = RestClient.builder().build();

        CodingCourseEnrollment enrollmentInFuture = new CodingCourseEnrollment(
                "JAVA",
                "John Smith",
                LocalDate.of(2222, 1, 1));


        Assertions.assertThatThrownBy(() ->
                        restClient.post()
                                .uri("http://localhost:" + port + "/enrollments")
                                .body(enrollmentInFuture)
                                .retrieve()
                                .toBodilessEntity()
                )
                .isInstanceOf(org.springframework.web.client.HttpClientErrorException.BadRequest.class)
                .hasFieldOrPropertyWithValue("statusCode", BAD_REQUEST);

	}


}
