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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EnrollmentControllerTest {

    @Mock
    private CodingCourseRepository codingCourseRepository;

    @InjectMocks
    private EnrollmentController enrollmentController;

    @Test
    void testCreateEnrollment_withFutureDate_returnsBadRequest() {
        CodingCourseEnrollment enrollment = mock(CodingCourseEnrollment.class);
        when(enrollment.getEnrollmentDate()).thenReturn(LocalDate.now().plusDays(1));

        ResponseEntity<Void> response = enrollmentController.createEnrollment(enrollment);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testCreateEnrollment_successfulSave_returnsCreated() {
        CodingCourseEnrollment enrollment = mock(CodingCourseEnrollment.class);
        when(enrollment.getEnrollmentDate()).thenReturn(LocalDate.now());
        when(codingCourseRepository.save(enrollment)).thenReturn(true);

        ResponseEntity<Void> response = enrollmentController.createEnrollment(enrollment);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void testCreateEnrollment_failedSave_returnsInternalServerError() {
        CodingCourseEnrollment enrollment = mock(CodingCourseEnrollment.class);
        when(enrollment.getEnrollmentDate()).thenReturn(LocalDate.now());

        when(codingCourseRepository.save(enrollment)).thenReturn(false);

        ResponseEntity<Void> response = enrollmentController.createEnrollment(enrollment);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

}

