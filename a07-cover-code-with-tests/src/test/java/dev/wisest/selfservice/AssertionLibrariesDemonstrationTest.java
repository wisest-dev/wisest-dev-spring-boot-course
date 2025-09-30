package dev.wisest.selfservice;

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

import org.assertj.core.api.Assertions;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AssertionLibrariesDemonstrationTest {

    public static final List<String> FELLOWSHIP = Arrays.asList(
            "Frodo", "Sam", "Pippin", "Merry", "Gandalf", "Aragorn", "Legolas", "Gimli", "Boromir");

    @Test
    void junit_assertEquals() {
        assertEquals(9, FELLOWSHIP.size());
    }

    @Test
    void junit_assertTrueWithCustomFailureMessage() {
        assertTrue(FELLOWSHIP.contains("Frodo"), "The fellowship should include Frodo");
    }

    @Test
    void hamcrest_assertThatWithHasSize() {
        MatcherAssert.assertThat(FELLOWSHIP, hasSize(9));
    }

    @Test
    void hamcrest_assertThatWithHasItem() {
        MatcherAssert.assertThat(FELLOWSHIP, hasItem("Frodo"));
    }

    @Test
    void aspectj_assertThatWithHasItem() {
        Assertions.assertThat(FELLOWSHIP)
                .hasSize(9)
                .contains("Frodo")
                .doesNotContain("Sauron");
    }

}
