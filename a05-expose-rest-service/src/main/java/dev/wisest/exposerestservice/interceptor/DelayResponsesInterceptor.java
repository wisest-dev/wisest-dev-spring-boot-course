package dev.wisest.exposerestservice.interceptor;

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

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

public class DelayResponsesInterceptor implements HandlerInterceptor {

    Logger logger = LoggerFactory.getLogger(DelayResponsesInterceptor.class);

    private final long delayAllRequestsMilliseconds;

    public DelayResponsesInterceptor(long delayAllRequestsMilliseconds) {
        logger.warn("I will delay all requests {} milliseconds.", delayAllRequestsMilliseconds);
        this.delayAllRequestsMilliseconds = delayAllRequestsMilliseconds;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("Request {} came in, I will delay the response for {} milliseconds.", request.getRequestURL(), delayAllRequestsMilliseconds);
        Thread.sleep(delayAllRequestsMilliseconds);
        return true;
    }

}
