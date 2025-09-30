package dev.wisest.selfservice.controller;

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

