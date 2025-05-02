package dev.wisest.exposerestservice.controller;

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
import dev.wisest.exposerestservice.repository.EnrollmentRepository;
import dev.wisest.exposerestservice.repository.exception.EnrollmentNotFoundException;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@RestController
public class EnrollmentController {

    private final EnrollmentRepository enrollmentRepository;

    EnrollmentController(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    @GetMapping("/courses/{courseId}/enrollments/all")
    Iterable<Enrollment> allOfCourse(@PathVariable String courseId) {

        Course courseDTO = new Course(courseId);
        return enrollmentRepository.findEnrollmentsByCourse(courseDTO);
    }

    @GetMapping("/courses/{courseId}/enrollments/{startDate}/{endDate}")
    Iterable<Enrollment> allCoursesBetween(@PathVariable String courseId, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate, @PathVariable  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        Course courseDTO = new Course(courseId); // DTO is not-so-good style

        return enrollmentRepository.findEnrollmentsByCourseAndEnrollmentDateBetween(courseDTO, startDate, endDate);
    }

    @GetMapping("/courses/{courseId}/enrollments/{enrollmentUuid}")
    Enrollment findByCourseAndEnrollmentId(@PathVariable String courseId, @PathVariable UUID enrollmentUuid) {

        return Optional.ofNullable(enrollmentRepository.findByCourseIdAndUuid(courseId, enrollmentUuid))
                .orElseThrow(() -> new EnrollmentNotFoundException(enrollmentUuid));
    }

    @PostMapping("/courses/{courseId}/enrollments")
    ResponseEntity<Enrollment> newEnrollment(@PathVariable String courseId, @Valid @RequestBody Enrollment newEnrollment) {

        Assert.isTrue(courseId.equals(newEnrollment.getCourse().getCourseId()),
                "The courseId in the enrollment payload must match the course id in the URL");

        Enrollment savedEnrollment = enrollmentRepository.save(newEnrollment);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEnrollment);
    }

    @DeleteMapping("/courses/{courseId}/enrollments/{enrollmentUuid}")
    void deleteEnrollment(@PathVariable String courseId, @PathVariable UUID enrollmentUuid) {

        Enrollment enrollment = Optional.ofNullable(enrollmentRepository.findByCourseIdAndUuid(courseId, enrollmentUuid))
                .orElseThrow(() -> new EnrollmentNotFoundException(courseId, enrollmentUuid));

        enrollmentRepository.delete(enrollment);
    }

}
