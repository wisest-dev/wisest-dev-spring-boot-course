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
import dev.wisest.connectdatabasejpa.model.Person;
import dev.wisest.connectdatabasejpa.repository.CourseRepository;
import dev.wisest.connectdatabasejpa.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Demo {

    private static final Logger log = LoggerFactory.getLogger(Demo.class);

    @Bean
    public CommandLineRunner demoQueries(PersonRepository personRepository, CourseRepository courseRepository) {
        return (args) -> {

            Person juhan = personRepository.save(new Person("Juhan Aasaru"));
            Person john = personRepository.save(new Person("John Smith"));


            // save a few courses
            courseRepository.save(new Course("BEGINNER_SPRING_BOOT","Java Spring Boot for Beginners", CourseTopic.JAVA, juhan));
            courseRepository.save(new Course("XROAD", "Consume and offer services via X-Road", CourseTopic.XROAD, juhan));
            courseRepository.save(new Course("JUNIOR_MORE_INTERVIEWS", "How to Land More Junior Software Engineer Job Interviews", CourseTopic.APPLYING_TO_JOB, juhan));
            courseRepository.save(new Course("SUCCEED_AS_SOFTWARE_ENGINEER", "Level Up Your Skills to Succeed in a Software Engineer Role", CourseTopic.APPLYING_TO_JOB, john));

            // fetch all courses
            log.info("Courses found with findAll():");
            log.info("-------------------------------");
            for (Course course : courseRepository.findAll()) {
                log.info(course.toString());
            }
            log.info("");

            // fetch an individual course by ID
            Course course = courseRepository.findByCourseId("BEGINNER_SPRING_BOOT");
            log.info("Course found with findByCourseId(BEGINNER_SPRING_BOOT):");
            log.info("--------------------------------");
            log.info(course.toString());
            log.info("");

            // fetch courses by topic
            log.info("Course found with findByTopicNotIn('APPLYING_TO_JOB, XROAD'):");
            log.info("--------------------------------------------");
            courseRepository.findByTopicNotIn(List.of(CourseTopic.APPLYING_TO_JOB, CourseTopic.XROAD))
                    .forEach(applyingCourse -> {
                        log.info(applyingCourse.toString());
                    });
            log.info("");

            // fetch all courses
            log.info("Courses by author Juhan:");
            log.info("-------------------------------");
            for (Course juhanCourse : courseRepository.findByAuthor(juhan)) {
                log.info(juhanCourse.toString());
            }
            log.info("");

            //
            log.info("There are {} courses in the database", courseRepository.count());

            courseRepository.save(new Course("TESTING_005", "Fifth course", CourseTopic.XROAD, juhan));
            log.info("After adding a course there are {} courses  in the database", courseRepository.count());

            Course fetchedCourse = courseRepository.findByTitleAndTopic("Fifth course", CourseTopic.XROAD);
            log.info("Fetched {} from database", fetchedCourse);
            courseRepository.delete(fetchedCourse);
            log.info("After deleting there are {} courses in the database", courseRepository.count());

            // fetch persons
            Person p1 = personRepository.findByNameIgnoreCase("juhan AASARU");
            log.info("Person looked up by ignoring case: {}", p1);


            log.info("Persons with short name:");
            log.info("-------------------------------");

            List<Person> personsWithShortName = personRepository.findByNameNotLongerThan(10);
            for (Person person : personsWithShortName) {
                log.info(person.toString());
            }
            log.info("");

            log.info("First person with a name starting with 'J':");
            log.info("-------------------------------");

            Person firstPersonStartingWithJ = personRepository.findFirstByNameStartingWithOrderByName("J");
            log.info("First person to start with J: {}", firstPersonStartingWithJ);
            log.info("");
            log.info("=== THE END OF DEMO ===");

        };
    }
}
