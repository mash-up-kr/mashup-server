package kr.mashup.branding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MashupAdminApplication {
    public static void main(String[] args) {
        System.setProperty("spring.config.name", "application,admin");
        SpringApplication.run(MashupAdminApplication.class, args);
    }
}
