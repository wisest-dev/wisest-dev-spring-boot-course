package dev.wisest.consumerest.userestclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

public class Util {
    static Logger log = LoggerFactory.getLogger(Util.class);

    public static LocalDate getRandomDate() {
        LocalDate minDate = LocalDate.of(1900, 1, 1);
        LocalDate maxDate = LocalDate.of(2000, 1, 1);
        int daysBetween = (int) java.time.temporal.ChronoUnit.DAYS.between(minDate, maxDate);
        LocalDate randomDate = minDate.plusDays(ThreadLocalRandom.current().nextInt(daysBetween));

        log.info("Generated random date {}", randomDate);

        return randomDate;
    }

}
