package dev.wisest.selfservice.util;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UsernameGeneratorTest {

    @Test
    public void generateUsername_validNames() {
        String username = UsernameGenerator.generateUsername("John", "Doe");
        assertEquals("jdoe", username);
    }

    @Test
    public void generateUsername_nonEnglishCharacters() {
        String username = UsernameGenerator.generateUsername("Ülo", "Õun");
        assertEquals("uoun", username, "Should substitute non-English letters with English most similar characters");
    }

    @Test
    public void generateUsername_firstNameEmpty() {
        assertThrows(IllegalArgumentException.class, () -> {
            UsernameGenerator.generateUsername("", "Doe");
        });
    }

    @Test
    public void generateUsername_lastNameEmpty() {
        assertThrows(IllegalArgumentException.class, () -> {
            UsernameGenerator.generateUsername("John", "");
        });
    }

    @Test
    public void generateUsername_firstNameNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            UsernameGenerator.generateUsername(null, "Doe");
        });
    }

    @Test
    public void generateUsername_lastNameNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            UsernameGenerator.generateUsername("John", null);
        });
    }

    @Test
    public void generateUsername_bothNamesEmpty() {
        assertThrows(IllegalArgumentException.class, () -> {
            UsernameGenerator.generateUsername("", "");
        });
    }

    @Test
    public void generateUsername_bothNamesNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            UsernameGenerator.generateUsername(null, null);
        });
    }

}
