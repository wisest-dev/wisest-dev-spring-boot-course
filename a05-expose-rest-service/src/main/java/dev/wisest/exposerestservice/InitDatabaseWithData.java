package dev.wisest.exposerestservice;

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

import dev.wisest.exposerestservice.model.Course;
import dev.wisest.exposerestservice.model.CourseTopic;
import dev.wisest.exposerestservice.model.Enrollment;
import dev.wisest.exposerestservice.model.Person;
import dev.wisest.exposerestservice.repository.CourseRepository;
import dev.wisest.exposerestservice.repository.EnrollmentRepository;
import dev.wisest.exposerestservice.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
class InitDatabaseWithData {

	private static final Logger log = LoggerFactory.getLogger(InitDatabaseWithData.class);

	@Bean
	CommandLineRunner initDatabase(PersonRepository personRepository, CourseRepository courseRepository, EnrollmentRepository enrollmentRepository) {

		return args -> {

			Person juhan = personRepository.save(new Person("Juhan Aasaru"));
			Person john = personRepository.save(new Person("John Smith"));


			// save a few courses
			Course springBoot = courseRepository.save(new Course("BEGINNER_SPRING_BOOT", "Java Spring Boot for Beginners", CourseTopic.JAVA, juhan));
			Course xRoad = courseRepository.save(new Course("XROAD", "Consume and offer services via X-Road", CourseTopic.XROAD, juhan));
			Course juniorMoreInterviews = courseRepository.save(new Course("JUNIOR_MORE_INTERVIEWS", "How to Land More Junior Software Engineer Job Interviews", CourseTopic.APPLYING_TO_JOB, juhan));
			Course softwareEngineer = courseRepository.save(new Course("SUCCEED_AS_SOFTWARE_ENGINEER", "Level Up Your Skills to Succeed in a Software Engineer Role", CourseTopic.APPLYING_TO_JOB, john));


			enrollmentRepository.save(new Enrollment(john, springBoot, LocalDate.of(2025,3,9)));
			enrollmentRepository.save(new Enrollment(john, springBoot, LocalDate.of(2025,3,9)));
			enrollmentRepository.save(new Enrollment(john, xRoad, LocalDate.of(2025,2,13)));
			enrollmentRepository.save(new Enrollment(juhan, xRoad, LocalDate.of(2025,3,1)));
			enrollmentRepository.save(new Enrollment(john, juniorMoreInterviews, LocalDate.of(2025,1,31)));

		};
	}
}
