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

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDate;


@Controller
public class SignupController implements WebMvcConfigurer {

	public static final LocalDate EARLIEST_ACCEPTED_DATE_OF_BIRTH = LocalDate.of(1900, 1, 1);

	@GetMapping("/signup")
	public String showForm(SignupForm signupForm) {
		return "signup/form";
	}

	@PostMapping("/signup")
	public String processSignup(@Valid SignupForm signupForm, BindingResult bindingResult, Model model) {

		validateDateOfBirth(signupForm, bindingResult);

		model.addAttribute("username", signupForm.getUsername());

		if (!bindingResult.hasErrors()) {
			System.out.println("no errors!");
		}

		if (bindingResult.hasErrors()) {
			return "signup/form";
		}

		return "signup/signup-completed";
	}

	private static void validateDateOfBirth(SignupForm signupForm, BindingResult bindingResult) {
		LocalDate dateOfBirthValueEnteredByTheUser = signupForm.getDateOfBirth();

		if (dateOfBirthValueEnteredByTheUser != null && dateOfBirthValueEnteredByTheUser.isBefore(EARLIEST_ACCEPTED_DATE_OF_BIRTH)) {
			int calculatedAge = LocalDate.now().getYear() - dateOfBirthValueEnteredByTheUser.getYear();

			bindingResult.addError(
					new FieldError("dateOfBirth", "dateOfBirth",
							dateOfBirthValueEnteredByTheUser,
							false,
							new String[]{"custom.error.too.old"},
							new Object[]{EARLIEST_ACCEPTED_DATE_OF_BIRTH, calculatedAge},
							null)

			);
		}
	}

}
