package dev.wisest.selfservice.util;

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