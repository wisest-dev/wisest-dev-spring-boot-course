package dev.wisest.exposerestservice.repository;

/*-
 * #%L
 * Code accompanying video course "Learn Spring Boot by Examining 10+ Practical Applications"
 * %%
 * Copyright (C) 2025 Juhan Aasaru and Wisest.dev
 * %%
 * The source code (including test code) in this repository is licensed under a
 * Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International Licence.
 * 
 * Attribution-NonCommercial-NoDerivatives 4.0 International Licence is a standard
 * form licence agreement that does not permit any commercial use or derivatives
 * of the original work. Under this licence: you may only distribute a verbatim
 * copy of the work. If you remix, transform, or build upon the work in any way then
 * you are not allowed to publish nor distribute modified material.
 * You must give appropriate credit and provide a link to the licence.
 * 
 * The full licence terms are available from
 * <http://creativecommons.org/licenses/by-nc-nd/4.0/legalcode>
 * 
 * All the code (including tests) contained herein should be attributed as:
 * © Juhan Aasaru https://Wisest.dev
 * #L%
 */

import dev.wisest.exposerestservice.model.Course;
import dev.wisest.exposerestservice.model.Enrollment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface EnrollmentRepository extends CrudRepository<Enrollment, UUID> {

    @Query("FROM Enrollment e WHERE e.course.courseId = :courseId AND e.uuid = :enrollmentUuid")
    Enrollment findByCourseIdAndUuid(String courseId, UUID enrollmentUuid);

    List<Enrollment> findEnrollmentsByCourse(Course course);

    List<Enrollment> findEnrollmentsByCourseAndEnrollmentDateBetween(Course course, LocalDate start, LocalDate end);

}
