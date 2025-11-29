package dev.wisest.selfservice.integration;

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

import dev.wisest.selfservice.controller.EnrollmentController;
import dev.wisest.selfservice.model.CodingCourseEnrollment;
import dev.wisest.selfservice.repository.CodingCourseRepository;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.json.JsonMapper;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EnrollmentController.class)
public class EnrollmentsEndpointWebMvcTest {

    @Resource
    private MockMvc mockMvc;

    @MockitoBean
    private CodingCourseRepository codingCourseRepository;

    @Resource
    private JsonMapper jsonMapper;


    @Test
    public void postEnrollment_enrollmentIsInThePastAndRepositoryReturnsTrue_createdStatusIsReturned() throws Exception {

        when(codingCourseRepository.save(any())).thenReturn(true);

        CodingCourseEnrollment enrollmentInPast = new CodingCourseEnrollment(
                "JAVA",
                "John Smith",
                LocalDate.of(2000, 1, 1));


        mockMvc.perform(post("/enrollments")
                        .contentType("application/json")
                        .content(jsonMapper.writeValueAsString(enrollmentInPast)))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    public void postEnrollment_enrollmentIsInThePastAndRepositoryReturnsFalse_internalServerErrorStatusIsReturned() throws Exception {

        when(codingCourseRepository.save(any())).thenReturn(false);

        CodingCourseEnrollment enrollmentInPast = new CodingCourseEnrollment(
                "JAVA",
                "John Smith",
                LocalDate.of(2000, 1, 1));


        mockMvc.perform(post("/enrollments")
                        .contentType("application/json")
                        .content(jsonMapper.writeValueAsString(enrollmentInPast)))
                .andExpect(status().isInternalServerError())
                .andDo(print());
    }


    @Test
    public void postEnrollment_enrollmentIsInTheFuture_badRequestStatusIsReturned() throws Exception {

        CodingCourseEnrollment enrollmentInFuture = new CodingCourseEnrollment(
                "JAVA",
                "John Smith",
                LocalDate.of(2222, 1, 1));


        mockMvc.perform(post("/enrollments")
                        .contentType("application/json")
                        .content(jsonMapper.writeValueAsString(enrollmentInFuture)))
                .andExpect(status().isBadRequest())
                .andDo(print());

        verify(codingCourseRepository, times(0)).save(any());

    }

}
