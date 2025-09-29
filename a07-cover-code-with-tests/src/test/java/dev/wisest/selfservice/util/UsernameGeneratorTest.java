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

import static org.junit.jupiter.api.Assertions.*;

class UsernameGeneratorTest {

    @Test
    void generateUsername_ValidNames_ReturnsExpectedUsername() {
        String result = UsernameGenerator.generateUsername("John", "Doe");
        assertEquals("jdoe", result);
    }

    @Test
    void generateUsername_NamesWithNonAsciiCharacters_RemovesNonAsciiCharacters() {
        String result = UsernameGenerator.generateUsername("Jöhn", "Döe");
        assertEquals("jdoe", result);
    }

    @Test
    void generateUsername_FirstNameIsEmpty_ThrowsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                UsernameGenerator.generateUsername("", "Doe")
        );
        assertEquals("First name and last name must not be null or empty", exception.getMessage());
    }

    @Test
    void generateUsername_LastNameIsEmpty_ThrowsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                UsernameGenerator.generateUsername("John", "")
        );
        assertEquals("First name and last name must not be null or empty", exception.getMessage());
    }

    @Test
    void generateUsername_FirstNameIsNull_ThrowsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                UsernameGenerator.generateUsername(null, "Doe")
        );
        assertEquals("First name and last name must not be null or empty", exception.getMessage());
    }

    @Test
    void generateUsername_LastNameIsNull_ThrowsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                UsernameGenerator.generateUsername("John", null)
        );
        assertEquals("First name and last name must not be null or empty", exception.getMessage());
    }

    @Test
    void generateUsername_NamesWithSpaces_ReturnsExpectedUsername() {
        String result = UsernameGenerator.generateUsername(" John ", " Doe ");
        assertEquals("jdoe", result);
    }

    @Test
    void generateUsername_NamesWithSpecialCharacters_RemovesSpecialCharacters() {
        String result = UsernameGenerator.generateUsername("J@hn", "D#e");
        assertEquals("jde", result);
    }
}
