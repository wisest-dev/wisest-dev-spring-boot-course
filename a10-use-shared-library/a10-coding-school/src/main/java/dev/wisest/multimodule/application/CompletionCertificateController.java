package dev.wisest.multimodule.application;

/*-
 * #%L
 * Code accompanying course "Learn Spring Boot by Examining 10+ Practical
 *                         Applications"
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

import dev.wisest.shared.library.CompletionCertificateService;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;

@Controller
public class CompletionCertificateController {

    private final CompletionCertificateService completionService;

    private final EnrollmentService enrollmentService;

    public CompletionCertificateController(CompletionCertificateService completionService, EnrollmentService enrollmentService) {
        this.completionService = completionService;
        this.enrollmentService = enrollmentService;
    }

    @GetMapping("/brand-name")
    public String brandName() {
        return completionService.getBrandName();
    }


    @RequestMapping(value = "/courses/{courseId}/enrollments/{enrollmentId}/certificate", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getPdfAsResponseEntity(@PathVariable String courseId, @PathVariable Long enrollmentId) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_PDF);

        Enrollment enrollment = enrollmentService.getEnrollment(courseId, enrollmentId);

        byte[] pdf = completionService.getCertificateOfCompletionPdf(enrollment.getCourseName(),
                enrollment.getStudentName(), enrollment.getCertificateNumber());

        return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
    }

}
