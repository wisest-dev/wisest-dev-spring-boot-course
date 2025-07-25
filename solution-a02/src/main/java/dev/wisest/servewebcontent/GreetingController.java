package dev.wisest.servewebcontent;

/*
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;

@Controller
public class GreetingController {

    Logger logger = LoggerFactory.getLogger(GreetingController.class);

    @PostMapping("/greeting")
    public String greeting(
            @RequestParam(name = "name", required = false, defaultValue = "World") String name,
            @RequestParam(name = "dob", required = false) String dateOfBirth,
            Model model) {

        Person person = new Person();
        person.setName(name);

        try {
            LocalDate parsedDateOfBirth = LocalDate.parse(dateOfBirth);

            if (parsedDateOfBirth.isAfter(LocalDate.now())) {
                logger.warn("Date of birth ({}) was set to future. ", dateOfBirth);
            } else {

                Period age = Period.between(parsedDateOfBirth, LocalDate.now());
                person.setAge(age.getYears());
                logger.info("Person's age is set to {} years", person.getAge());
            }
        } catch (DateTimeParseException | NullPointerException pe) {
            logger.warn("Date of birth had invalid format. '{}' was provided ", dateOfBirth);
        }

        model.addAttribute("user", person);

        if (person.getAge() != null && person.getAge() < 18) {
            return "templates/greeting-for-minor.html";
        } else {
            return "templates/greeting.html";
        }

    }

}
