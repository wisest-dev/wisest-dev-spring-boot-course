package dev.wisest.consumerest.userestclient;

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
import dev.wisest.consumerest.model.Person;
import dev.wisest.consumerest.model.WebCourse;
import dev.wisest.consumerest.repository.restclient.EnrollmentRepositoryWithRestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("solution")
public class SolutionWithRestClient {

    Logger log = LoggerFactory.getLogger(SolutionWithRestClient.class);

    @Bean
    public CommandLineRunner run(EnrollmentRepositoryWithRestClient enrollmentRepositoryWithRestClient) {
        return args -> {

            Enrollment enrollment = new Enrollment(
                    new Person(1L),
                    new WebCourse("XROAD"),
                    Util.getRandomDate());


            boolean firstCall = enrollmentRepositoryWithRestClient.addOrUpdateEnrollment(enrollment);
            log.info("A new enrollment was added on first call: {}", firstCall);

            boolean secondCall = enrollmentRepositoryWithRestClient.addOrUpdateEnrollment(enrollment);
            log.info("A new enrollment was added on second call: {}", secondCall);

            enrollment.getStudent().setName("Mary");
            try {
                boolean thirdCall = enrollmentRepositoryWithRestClient.addOrUpdateEnrollment(enrollment);
                log.info("A new enrollment was added on third call: {}", secondCall);
            }
            catch (IllegalArgumentException illegalArgumentException) {
                log.error("IllegalArgumentException was thrown on third call ", illegalArgumentException);
            }

        };
    }

}
