package dev.wisest.selfservice.controller;

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

import dev.wisest.selfservice.service.CourseService;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collection;

import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class CourseControllerMockTest {

    @Mock
    CourseService courseService;

    @InjectMocks
    CourseController courseController;

    @Test
    void getCourses() {

        Mockito.when(courseService.getCourseTitles()).thenReturn(Arrays.asList("D course", "C course"));

        Collection<String> courses = courseController.getCoursesSorted();

        Mockito.verify(courseService, times(1)).getCourseTitles();

        MatcherAssert.assertThat(courses, Matchers.contains("C course", "D course"));
    }

}
