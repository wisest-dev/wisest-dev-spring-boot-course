package dev.wisest.connectdatabasejpa;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class AppDebugConfig {

    @Bean
    public CommandLineRunner beanNamePrinter(ApplicationContext ctx) {
        System.out.println("The method that returns a CommandLineRunner bean was called.");
        CommandLineRunner commandLineRunner = new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                System.out.println("Let's list the beans provided by Spring Boot:");

                String[] beanNames = ctx.getBeanDefinitionNames();
                Arrays.sort(beanNames);
                for (String beanName : beanNames) {
                    System.out.println(beanName);
                }
            }
        };
        System.out.println("The CommandLineRunner bean has been created");
        return commandLineRunner;
    }

}
