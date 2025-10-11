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

import dev.wisest.selfservice.model.CodingCourseEnrollment;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EnrollmentEndpointMockMvcTest {

    @Resource
    private MockMvc mockMvc;

    @Test
    public void postEnrollment_enrollmentIsInThePast_createdStatusIsReturned() throws Exception {

        mockMvc.perform(post("/enrollments")
                        .contentType("application/json")
                        .content("""
                                {
                                  "courseId": "JAVA",
                                  "enrollmentDate": "2000-01-01",
                                  "studentName": "John Smith"
                                }
                                """))
                .andExpect(status().isCreated())
                .andDo(print());
    }


    @Test
    public void postEnrollment_enrollmentCourseIsDevops_internalServerErrorStatusIsReturned() throws Exception {



        mockMvc.perform(post("/enrollments")
                        .contentType("application/json")
                        .content("""
                                {
                                  "courseId": "DEVOPS_INTERMEDIATE",
                                  "enrollmentDate": "2000-01-01",
                                  "studentName": "John Smith"
                                }
                                """))
                .andExpect(status().isInternalServerError())
                .andDo(print());
    }


    @Test
    public void postEnrollment_enrollmentIsInTheFuture_badRequestStatusIsReturned() throws Exception {

        mockMvc.perform(post("/enrollments")
                        .contentType("application/json")
                        .content("""
                                {
                                  "courseId": "JAVA",
                                  "enrollmentDate": "2222-01-01",
                                  "studentName": "John Smith"
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andDo(print());

    }


}
