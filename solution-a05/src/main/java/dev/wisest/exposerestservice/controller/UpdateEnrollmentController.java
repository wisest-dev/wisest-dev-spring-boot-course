package dev.wisest.exposerestservice.controller;

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

import dev.wisest.exposerestservice.controller.exception.EnrollmentUpdateNotAllowedException;
import dev.wisest.exposerestservice.model.Course;
import dev.wisest.exposerestservice.model.Enrollment;
import dev.wisest.exposerestservice.repository.EnrollmentRepository;
import dev.wisest.exposerestservice.repository.exception.EnrollmentNotFoundException;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@RestController
public class UpdateEnrollmentController {

    private final EnrollmentRepository enrollmentRepository;

    UpdateEnrollmentController(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    @PutMapping("/courses/{courseId}/enrollments")
    ResponseEntity<Enrollment> updateEnrollment(
            @PathVariable String courseId,
            @Valid @RequestBody Enrollment postedEnrollment) {

        Assert.isTrue(courseId.equals(postedEnrollment.getCourse().getCourseId()),
                "The courseId in path and body must match");

        Optional<Enrollment> existing = enrollmentRepository.findFirstByCourseAndStudentAndEnrollmentDate(
                postedEnrollment.getCourse(), postedEnrollment.getStudent(), postedEnrollment.getEnrollmentDate());

        if (existing.isEmpty()) {
            Enrollment savedEnrollment = enrollmentRepository.save(postedEnrollment);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(savedEnrollment);
        }

        Enrollment existingEnrollment = existing.get();

        if (StringUtils.hasLength(postedEnrollment.getStudent().getName())
                && !postedEnrollment.getStudent().getName().equalsIgnoreCase(existingEnrollment.getStudent().getName())) {
            throw new EnrollmentUpdateNotAllowedException("Cannot change student name");
        }
        else {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(existingEnrollment);
        }

    }

}
