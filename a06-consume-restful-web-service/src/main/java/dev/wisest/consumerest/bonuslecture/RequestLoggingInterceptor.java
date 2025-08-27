package dev.wisest.consumerest.bonuslecture;

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

import jakarta.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class RequestLoggingInterceptor implements ClientHttpRequestInterceptor {
    Logger log = LoggerFactory.getLogger(RequestLoggingInterceptor.class);

    @Override
    public ClientHttpResponse intercept(@Nonnull HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {

        if (log.isDebugEnabled()) {

            String bodySent = new String(body, StandardCharsets.UTF_8);

            log.debug("The JSON that was sent: {}", bodySent);
        }

        ClientHttpResponse methodCallResult = execution.execute(request, body);

        if (log.isDebugEnabled()) {
            byte[] responseBody = methodCallResult.getBody().readAllBytes();
            String result = new String(responseBody, StandardCharsets.UTF_8);
            log.debug("The JSON that was received: {}", result);
            return new BufferingClientHttpResponseWrapper(methodCallResult, responseBody);
        }
        else {
            return methodCallResult;
        }

    }
}
