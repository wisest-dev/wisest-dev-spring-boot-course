package dev.wisest.consumerest.bonuslecture;

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
import dev.wisest.consumerest.model.Person;
import dev.wisest.consumerest.model.WebCourse;
import dev.wisest.consumerest.repository.restclient.CourseRepositoryWithRestClient;
import dev.wisest.consumerest.repository.restclient.EnrollmentRepositoryWithRestClient;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;

@Configuration
@Profile("bonusLecture")
public class ConsumerWithRestClient {
    Logger log = LoggerFactory.getLogger(ConsumerWithRestClient.class);

    @Resource
    CourseRepositoryWithRestClient courseRepositoryWithRestClient;

    @Bean
    public CommandLineRunner run(RestClient restClient, EnrollmentRepositoryWithRestClient enrollmentRepository, EnrollmentRepositoryWithRestClient enrollmentRepositoryWithRestClient) throws Exception {
        return args -> {

            WebCourse webCourse = courseRepositoryWithRestClient.getCourse("XROAD");

            Enrollment enrollmentToAdd = new Enrollment(
                    new Person(1L),
                    new WebCourse("XROAD"),
                    LocalDate.of(2024, 2, 29));

            Enrollment addedEnrollment = enrollmentRepositoryWithRestClient
                    .addEnrollment(enrollmentToAdd);

            enrollmentRepositoryWithRestClient.deleteEnrollment(
                    addedEnrollment.getCourse().getId(),
                    addedEnrollment.getUuid());

        };
    }

}
