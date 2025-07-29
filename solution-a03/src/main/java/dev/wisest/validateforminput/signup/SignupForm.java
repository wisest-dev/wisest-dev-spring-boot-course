package dev.wisest.validateforminput.signup;

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

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SignupForm {

	@Email
	@NotBlank(message = "Please pick a username")
	private String username;

	@NotNull(message = "{springBootExpertise.value.not.provided}")
	@PositiveOrZero(message = "Please enter a non-negative number, you entered ${validatedValue}")
	@Digits(integer = 3, fraction = 2, message = "Please enter level of detail up to 3 digits before and 2 after the decimal point")
	@Max(value=99, message = "Expertise level cannot exceed {value}")
	@org.springframework.format.annotation.NumberFormat(style = org.springframework.format.annotation.NumberFormat.Style.NUMBER)
	private BigDecimal springBootExpertise;

	@Past
	@org.springframework.format.annotation.DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dateOfBirth;

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public BigDecimal getSpringBootExpertise() {
		return springBootExpertise;
	}

	public void setSpringBootExpertise(BigDecimal springBootExpertise) {
		this.springBootExpertise = springBootExpertise;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String toString() {
		return "Signup(username: " + this.username + ", springBootExpertise: " + this.springBootExpertise + ", dateOfBirth:"+ dateOfBirth + ")";
	}

}
