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

import dev.wisest.consumerest.model.WebCourse;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class CourseRepositoryWithRestTemplate {
    Logger log = LoggerFactory.getLogger(CourseRepositoryWithRestTemplate.class);

    @Resource
    private RestTemplate restTemplate;

    public WebCourse getCourse(String courseId) {

        WebCourse course = restTemplate.getForObject(
                "/courses/{courseId}",
                WebCourse.class,
                courseId);
        if (course == null) {
            log.warn("REST TEMPLATE :: Course with ID {} not found", courseId);
        }
        else {
            log.info("REST TEMPLATE :: Found course '{}' by {} on topic {}",
                    course.getTitle(), course.getAuthor().getName(), course.getCourseTopic());
        }
        return course;

    }

    public WebCourse getCourseWithHeaders(String courseId) {

        ResponseEntity<WebCourse> responseWithHeaders = restTemplate.getForEntity(
                "/courses/{courseId}",
                WebCourse.class,
                courseId);

        WebCourse course = responseWithHeaders.getBody();
        HttpStatusCode status = responseWithHeaders.getStatusCode();

        if (course == null) {
            log.warn("REST TEMPLATE :: Course with ID {} not found. Status code was {}", courseId, status);
        }
        else {
            log.info("REST TEMPLATE :: Course '{}' by {}. Status: {}", course.getTitle(), course.getAuthor(), status);
        }

        return course;
    }

}
