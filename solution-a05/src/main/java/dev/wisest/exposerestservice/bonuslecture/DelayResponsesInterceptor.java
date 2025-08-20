package dev.wisest.exposerestservice.bonuslecture;

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

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

public class DelayResponsesInterceptor implements HandlerInterceptor {

    Logger logger = LoggerFactory.getLogger(DelayResponsesInterceptor.class);

    private final long delayAllDeleteRequestsMilliseconds;

    public DelayResponsesInterceptor(long delayAllDeleteRequestsMilliseconds) {
        logger.warn("I will delay all requests {} milliseconds.", delayAllDeleteRequestsMilliseconds);
        this.delayAllDeleteRequestsMilliseconds = delayAllDeleteRequestsMilliseconds;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (request.getMethod().equalsIgnoreCase("DELETE")) {
            logger.info("Delete request {} came in, I will delay the response for {} milliseconds.", request.getRequestURL(), delayAllDeleteRequestsMilliseconds);
            Thread.sleep(delayAllDeleteRequestsMilliseconds);
        }

        return true;
    }

}
