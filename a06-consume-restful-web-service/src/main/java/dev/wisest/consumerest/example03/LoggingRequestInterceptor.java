package dev.wisest.consumerest.example03;

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

import jakarta.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class LoggingRequestInterceptor implements ClientHttpRequestInterceptor {
    Logger log = LoggerFactory.getLogger(LoggingRequestInterceptor.class);

    @Override
    public ClientHttpResponse intercept(@Nonnull HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {


        if (log.isDebugEnabled()) {
            log.debug("The JSON that was sent: {}", body);
        }

        ClientHttpResponse methodCallResult = execution.execute(request, body);

        if (log.isDebugEnabled()) {

            String result = new String(methodCallResult.getBody().readAllBytes(), StandardCharsets.UTF_8);

            log.debug("The JSON that was received: {}", result);
        }
        return methodCallResult;

    }
}
