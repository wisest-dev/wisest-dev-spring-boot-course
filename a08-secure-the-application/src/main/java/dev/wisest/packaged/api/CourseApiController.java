package dev.wisest.packaged.api;

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

import dev.wisest.packaged.model.Course;
import dev.wisest.packaged.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

import static java.util.Arrays.asList;

@RestController
public class CourseApiController {

    Logger logger = LoggerFactory.getLogger(CourseApiController.class);

    @GetMapping("/api/courses")
    Collection<Course> getCourses() {

        logger.info("getCourses");

        Person austin = new Person("Austin Powers");
        austin.setPersonId(3L);

        Course prometheus = new Course("PROMETHEUS_MONITORING", "Monitoring with Prometheus", austin);
        Course docker = new Course("DOKER_BEGINNER", "Docker for Beginners", austin);

        return asList(prometheus, docker);
    }

}
