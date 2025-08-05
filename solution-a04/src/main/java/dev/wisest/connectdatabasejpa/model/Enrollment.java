package dev.wisest.connectdatabasejpa.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Enrollment {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Person student;

    @ManyToOne
    private Course course;
    private LocalDate enrollmentDate;

    public Enrollment() {
    }

    public Enrollment(Person student, Course course, LocalDate enrollmentDate) {
        this.student = student;
        this.course = course;
        this.enrollmentDate = enrollmentDate;
    }

    @Override
    public String toString() {
        return String.format(
                "Enrollment[studentId=%s, courseId='%s', enrollmentDate='%s']",
                student.getPersonId(), course.getCourseId(), enrollmentDate);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Person getStudent() {
        return student;
    }

    public void setStudent(Person student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(LocalDate enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }
}
