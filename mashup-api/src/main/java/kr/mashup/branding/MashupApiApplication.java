package kr.mashup.branding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MashupApiApplication {
    public static void main(String[] args) {
        System.setProperty("spring.config.name", "application,api");
        SpringApplication.run(MashupApiApplication.class, args);
    }
}
