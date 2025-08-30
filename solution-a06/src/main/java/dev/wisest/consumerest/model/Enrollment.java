package dev.wisest.consumerest.model;

/*-
 * #%L
 * "Learn Spring Boot by Examining 10+ Practical Applications" webCourse materials
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


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Enrollment {
    private UUID uuid;

    private Person student;

    @JsonProperty("course")
    private WebCourse webCourse;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate enrollmentDate;

    public Enrollment() {
    }

    public Enrollment(UUID uuid) {
        this.uuid = uuid;
    }

    public Enrollment(Person student, WebCourse webCourse, LocalDate enrollmentDate) {
        this.student = student;
        this.webCourse = webCourse;
        this.enrollmentDate = enrollmentDate;
    }

    public Enrollment(UUID uuid, Person student, WebCourse webCourse, LocalDate enrollmentDate) {
        this.uuid = uuid;
        this.student = student;
        this.webCourse = webCourse;
        this.enrollmentDate = enrollmentDate;
    }

    public void setUuid(UUID id) {
        this.uuid = id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Person getStudent() {
        return student;
    }

    public void setStudent(Person student) {
        this.student = student;
    }

    public WebCourse getCourse() {
        return webCourse;
    }

    public void setCourse(WebCourse webCourse) {
        this.webCourse = webCourse;
    }

    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(LocalDate enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    @Override
    public String toString() {
        return "Enrollment{" +
                "uuid=" + uuid +
                ", student=" + student +
                ", course=" + webCourse +
                ", enrollmentDate=" + enrollmentDate +
                '}';
    }
}
