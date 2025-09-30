package dev.wisest.selfservice.controller;

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

import dev.wisest.selfservice.model.CodingCourseEnrollment;
import dev.wisest.selfservice.repository.CodingCourseRepository;
import jakarta.annotation.Nonnull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class CodingCourseEnrollmentController {

    private final CodingCourseRepository codingCourseRepository;


    CodingCourseEnrollmentController(CodingCourseRepository codingCourseRepository) {
        this.codingCourseRepository = codingCourseRepository;

    }

    @PostMapping("/courses/coding/{courseId}/enrollments")
    ResponseEntity<Void> createEnrollment(@PathVariable String courseId,
                                          @Nonnull @RequestBody CodingCourseEnrollment newEnrollment) {

        Assert.isTrue(newEnrollment.getEnrollmentDate().isBefore(LocalDate.now().plusDays(1)),
                "Enrollment date must be on today or in the past");


        boolean isSaved = codingCourseRepository.save(newEnrollment);

        if (isSaved) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }


}
