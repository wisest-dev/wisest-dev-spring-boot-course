package dev.wisest.exposerestservice.controller.exception;

public class EnrollmentUpdateNotAllowedException extends RuntimeException {

    public EnrollmentUpdateNotAllowedException(String message) {
        super(message);
    }

}