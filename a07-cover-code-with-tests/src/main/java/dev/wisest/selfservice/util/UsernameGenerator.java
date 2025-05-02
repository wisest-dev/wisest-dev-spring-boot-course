package dev.wisest.selfservice.util;

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

import java.text.Normalizer;
import java.util.Locale;

public class UsernameGenerator {

    public static String generateUsername(String firstName, String lastName) {

        if (firstName == null || firstName.isEmpty() || lastName == null || lastName.isEmpty()) {
            throw new IllegalArgumentException("First name and last name must not be null or empty");
        }

        // Normalize and remove non-ASCII characters
        String normalizedFirstName = Normalizer.normalize(firstName, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");

        String normalizedLastName = Normalizer.normalize(lastName, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");

        // Create username
        String username = (normalizedFirstName.charAt(0) + normalizedLastName).toLowerCase(Locale.ROOT);

        return username;
    }

}
