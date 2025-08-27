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

import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class BufferingClientHttpResponseWrapper  implements ClientHttpResponse {

    private final ClientHttpResponse response;  // Original response
    private final byte[] responseBody;  // Buffered response body

    public BufferingClientHttpResponseWrapper(ClientHttpResponse response, byte[] responseBody) throws IOException {
        this.response = response;
        this.responseBody = responseBody;  // Buffer the response body
    }

    private byte[] streamToByteArray(InputStream inputStream) throws IOException {
        // Read the input stream into a byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        inputStream.transferTo(baos);
        return baos.toByteArray(); // Return the byte array
    }

    @Override
    public InputStream getBody() {
        // Return an InputStream from the buffered response body
        return new ByteArrayInputStream(responseBody);
    }

    @Override
    public HttpStatusCode getStatusCode() throws IOException {
        return response.getStatusCode();  // Delegate to the original response
    }

    @Override
    public String getStatusText() throws IOException {
        return response.getStatusText();  // Delegate to the original response
    }

    @Override
    public void close() {
        response.close();  // Close the original response
    }

    @Override
    public org.springframework.http.HttpHeaders getHeaders() {
        return response.getHeaders();  // Delegate to the original response
    }
}
