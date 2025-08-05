
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
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

@DataJpaTest
public class CourseRepositoryTest {
	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private PersonRepository personRepository;

	@Test
	public void findByTopicNotIn() {
		Person johnJohnny = new Person("John Johnny");
		entityManager.persist(johnJohnny);

		Course course1 = new Course("COURSE01", "first course", CourseTopic.JAVA, johnJohnny);
		entityManager.persist(course1);
		Course course2 = new Course("COURSE02", "second course", CourseTopic.APPLYING_TO_JOB, johnJohnny);
		entityManager.persist(course2);
		List<Course> resultLIst = courseRepository.findByTopicNotIn(List.of(CourseTopic.APPLYING_TO_JOB));

		Assertions.assertThat(resultLIst).extracting(Course::getTopic).containsOnly(course1.getTopic());
	}

	@Test
	public void findByAuthor() {
		Person johnJohnny = new Person("John Johnny");
		entityManager.persist(johnJohnny);

		Course course = new Course("COURSE_ABC", "first course", CourseTopic.JAVA, johnJohnny);
		entityManager.persist(course);

		Person foundAuthor = personRepository.findById(johnJohnny.getPersonId()).get();

		List<Course> resultList = courseRepository.findByAuthor(foundAuthor);

		Assertions.assertThat(resultList).extracting(Course::getCourseId).containsOnly("COURSE_ABC");
	}

}
