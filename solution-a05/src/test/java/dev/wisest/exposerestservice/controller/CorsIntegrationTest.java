package dev.wisest.exposerestservice.controller;

import dev.wisest.exposerestservice.model.Enrollment;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CorsIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void sendPatchRequest_methodNotSupportedByCorsConfiguration() {

        Enrollment enrollment = new Enrollment();

        String response = this.restTemplate.patchForObject(
                "http://localhost:{port}/courses/{courseId}/enrollments",
                enrollment,
                String.class,
                port, "SUCCEED_AS_SOFTWARE_ENGINEER");

        Assertions.assertThat(response).contains("Method Not Allowed");

    }
}
