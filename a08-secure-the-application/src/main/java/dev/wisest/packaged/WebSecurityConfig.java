package dev.wisest.packaged;

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

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    /*
        @Bean
        @Order(1)
        public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
            http
                    .securityMatcher("/api/**")
                    .authorizeHttpRequests(authorize -> authorize
                            .requestMatchers("/api/courses").hasRole("USER")
                            .requestMatchers("/api/courses/{courseId}").hasRole("USER")
                            .requestMatchers("/api/courses/{courseId}/enrollments**").hasRole("ADMIN")
                    )
                    .httpBasic(Customizer.withDefaults());
            return http.build();
        }
    */
    @Bean
	public SecurityFilterChain springSecurityFilterChain2(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/", "/intro").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll()
                )
                .logout((logout) -> logout.permitAll());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        UserDetails user =
                User.builder()
                        .username("user") // TODO regular
                        .password(encoder.encode("password"))
                        .roles("USER")
                        .build();
        UserDetails userAdmin =
                User.builder()
                        .username("regular-and-admin")
                        .password(encoder.encode("password"))
                        .roles("USER", "ADMIN")
                        .build();

        UserDetails adminOnly =
                User.builder()
                        .username("admin-only")
                        .password(encoder.encode("password"))
                        .roles("ADMIN")
                        .build();

        return new InMemoryUserDetailsManager(user, userAdmin, adminOnly);
    }

}
