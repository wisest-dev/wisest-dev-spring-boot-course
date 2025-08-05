package dev.wisest.connectdatabasejpa;

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

import dev.wisest.connectdatabasejpa.model.Course;
import dev.wisest.connectdatabasejpa.model.CourseTopic;
import dev.wisest.connectdatabasejpa.model.Enrollment;
import dev.wisest.connectdatabasejpa.model.Person;
import dev.wisest.connectdatabasejpa.repository.CourseRepository;
import dev.wisest.connectdatabasejpa.repository.EnrollmentRepository;
import dev.wisest.connectdatabasejpa.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class Demo {

    private static final Logger log = LoggerFactory.getLogger(Demo.class);

    @Bean
    public CommandLineRunner solutionQueries(PersonRepository personRepository,
                                             CourseRepository courseRepository,
                                             EnrollmentRepository enrollmentRepository) {
        return (args) -> {

            Person juhan = personRepository.save(new Person("Juhan Aasaru"));
            Person john = personRepository.save(new Person("John Smith"));


            courseRepository.save(
                    new Course("XROAD",
                            "Consume and offer services via X-Road",
                            CourseTopic.XROAD,
                            juhan));


            log.info("=== START OF THE SOLUTION ===");

            Course xroadCourse = courseRepository.findByCourseId("XROAD");

            enrollmentRepository.save(new Enrollment(john, xroadCourse, LocalDate.of(2025, 3, 15)));

            enrollmentRepository.save(new Enrollment(john, xroadCourse, LocalDate.of(2025, 2, 28)));
            enrollmentRepository.save(new Enrollment(john, xroadCourse, LocalDate.of(2025, 2, 1)));
            enrollmentRepository.save(new Enrollment(john, xroadCourse, LocalDate.of(2025, 2, 15)));

            enrollmentRepository.save(new Enrollment(john, xroadCourse, LocalDate.of(2025, 3, 1)));
            enrollmentRepository.save(new Enrollment(john, xroadCourse, LocalDate.of(2025, 3, 31)));



            List<Enrollment> februaryXroadEnrollments = enrollmentRepository.findByCourseAndEnrollmentDateBetweenOrderByEnrollmentDate(xroadCourse,
                    LocalDate.of(2025, 2, 1),
                    LocalDate.of(2025, 2, 28));

            februaryXroadEnrollments.forEach(enrollment -> log.info("Enrollment in February for X-Road course: {}", enrollment));

            log.info("=== END OF THE SOLUTION ===");

        };
    }
}
