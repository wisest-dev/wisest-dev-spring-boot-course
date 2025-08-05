package dev.wisest.connectdatabasejpa.repository;

import dev.wisest.connectdatabasejpa.model.Course;
import dev.wisest.connectdatabasejpa.model.Enrollment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EnrollmentRepository extends CrudRepository<Enrollment, Long> {

    List<Enrollment> findByCourseAndEnrollmentDateBetweenOrderByEnrollmentDate(Course course, LocalDate enrollmentStartDate, LocalDate enrollmentEndDate);

}
