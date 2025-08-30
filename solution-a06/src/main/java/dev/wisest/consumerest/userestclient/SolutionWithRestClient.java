package dev.wisest.consumerest.userestclient;

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
                log.error("IllegalArgumentException was thrown on third call");
            }

        };
    }

}
