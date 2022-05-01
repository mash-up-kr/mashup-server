package kr.mashup.branding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MashupRecruitApplication {
    public static void main(String[] args) {
        System.setProperty("spring.config.name", "application,recruit");
        SpringApplication.run(MashupRecruitApplication.class, args);
    }
}
