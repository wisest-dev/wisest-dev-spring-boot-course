package dev.wisest.consumerest.repository.resttemplate;

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

import dev.wisest.consumerest.model.Enrollment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.UUID;

@Repository
public class EnrollmentRepositoryWithRestTemplate {
    Logger log = LoggerFactory.getLogger(EnrollmentRepositoryWithRestTemplate.class);

    private final RestTemplate restTemplate;

    public EnrollmentRepositoryWithRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public UUID addEnrollment(Enrollment enrollmentToAdd) {
        ResponseEntity<Enrollment> addedEnrollmentResponse = restTemplate.postForEntity(
                "/courses/{courseId}/enrollments",
                enrollmentToAdd,
                Enrollment.class,
                "XROAD"
        );


        if (addedEnrollmentResponse.getStatusCode() == HttpStatus.CREATED) {
            Enrollment addedEnrollment = Objects.requireNonNull(addedEnrollmentResponse.getBody());
            log.info("Created new enrollment {}", addedEnrollment.getUuid());


            return addedEnrollment.getUuid();

        } else {
            log.info("Creating an enrollment failed with status code {}", addedEnrollmentResponse.getStatusCode());
        }
        return null;
    }


    public void deleteEnrollment(String courseId, UUID enrollmentUuidToDelete) {
        ResponseEntity<Void> deleteResult = restTemplate
                .exchange("/courses/{courseId}/enrollments/{enrollmentId}",
                        HttpMethod.DELETE,
                        null,
                        Void.class,
                        courseId, enrollmentUuidToDelete
                );

        log.info("Deleting of the newly created enrollment resulted with status code {}", deleteResult.getStatusCode());
    }

}
