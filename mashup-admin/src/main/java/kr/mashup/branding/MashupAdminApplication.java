package kr.mashup.branding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MashupAdminApplication {
    public static void main(String[] args) {
        System.setProperty("spring.config.name", "application,admin");
        SpringApplication.run(MashupAdminApplication.class, args);
    }
}
