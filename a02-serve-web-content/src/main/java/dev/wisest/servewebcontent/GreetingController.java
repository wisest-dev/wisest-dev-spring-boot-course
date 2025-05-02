package dev.wisest.servewebcontent;

/*
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingController {

    Logger logger = LoggerFactory.getLogger(GreetingController.class);

    @GetMapping("/greeting")
    public String greeting(
            @RequestParam(name = "name", required = false, defaultValue = "World") String name,
            @RequestParam(name = "age", required = false) String age,
            Model model) {

        Person person = new Person();
        person.setName(name);

        try {
            int parsedAge = Integer.parseInt(age);

            if (parsedAge < 0) {
                logger.warn("A negative number ({}) was provided as age. ", age);
            } else {
                person.setAge(parsedAge);
            }
        } catch (NumberFormatException nfe) {
            logger.warn("Age wasn't a number. '{}' was provided ", age);
        }

        model.addAttribute("user", person);
        return "greeting";
    }

}
