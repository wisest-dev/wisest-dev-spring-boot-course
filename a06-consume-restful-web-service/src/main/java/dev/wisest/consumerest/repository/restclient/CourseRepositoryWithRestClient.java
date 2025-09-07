package dev.wisest.consumerest.repository.restclient;

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

import dev.wisest.consumerest.model.course;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClient;

@Repository
public class CourseRepositoryWithRestClient {
    Logger log = LoggerFactory.getLogger(CourseRepositoryWithRestClient.class);

    @Resource
    private RestClient restClient;

    public course getCourse(String courseId) {

        RestClient.ResponseSpec xroadCourseResponse = restClient.get()
                .uri("/courses/{courseId}", courseId)
                .retrieve();

        course fetchedcourse = xroadCourseResponse.body(course.class);

        log.info("REST CLIENT :: XROAD course: {}", fetchedcourse);

        ResponseEntity<course> courseEntity = xroadCourseResponse.toEntity(course.class);

        log.info("REST CLIENT :: XROAD course payload was received with content type: {}", courseEntity.getHeaders().getContentType());
        log.info("REST CLIENT :: XROAD course payload: {}", courseEntity.getBody());

        return fetchedcourse;
    }

}
