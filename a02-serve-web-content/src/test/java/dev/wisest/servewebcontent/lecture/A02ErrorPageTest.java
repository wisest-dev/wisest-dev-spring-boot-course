package dev.wisest.servewebcontent.lecture;

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

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class A02ErrorPageTest {

    @Value(value = "${local.server.port}")
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void whenGettingNonExistingPageCustomErrorMessageIsReturned() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.TEXT_HTML));

        String randomString = LocalDateTime.now().toString();

        ResponseEntity<String> responseEntity = this.restTemplate.exchange("http://localhost:" + port + "/page" + randomString,
                HttpMethod.GET, new HttpEntity<Void>(headers), String.class);

        assertThat(responseEntity.getBody())
                .as("'Whitelabel Error Page' is not returned by Spring Boot when requesting non-existing page")
                .doesNotContain("Whitelabel Error Page");

        assertThat(responseEntity.getBody())
                .as("Custom error page with with message 'An error has occurred' is returned for not found page")
                .contains("An error has occurred");
    }

}
