package dev.wisest.consumerest.repository.restclient;

/*-
 * #%L
 * "Learn Spring Boot by Examining 10+ Practical Applications" webCourse materials
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

import dev.wisest.consumerest.model.Enrollment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@Repository
public class EnrollmentRepositoryWithRestClient {
    Logger log = LoggerFactory.getLogger(EnrollmentRepositoryWithRestClient.class);

    private final RestClient restClient;

    public EnrollmentRepositoryWithRestClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public boolean addOrUpdateEnrollment(Enrollment enrollmentToAddOrUpdate) {
        String courseId = enrollmentToAddOrUpdate.getCourse().getId();
        UUID enrollmentId = enrollmentToAddOrUpdate.getUuid();
        log.info("REST CLIENT :: About to add or update an enrollment with id {} in course {}", enrollmentId, courseId);

        ResponseEntity<Void> addOrUpdateResult = restClient
                .put()
                .uri("/courses/{courseId}/enrollments", courseId)
                .body(enrollmentToAddOrUpdate)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,(req, res)->{
                     throw new IllegalArgumentException("API rejected our payload with error " + res.getStatusCode());
                 })
                .toBodilessEntity();

        return addOrUpdateResult.getStatusCode().isSameCodeAs(HttpStatus.CREATED);
    }

}
