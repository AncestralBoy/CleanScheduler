package org.project.cleanscheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CleanSchedulerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CleanSchedulerApplication.class, args);
    }

}
