package kr.mashup.branding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MashupBatchApplication {
    public static void main(String[] args) {
        System.setProperty("spring.config.name", "application,batch");
        SpringApplication.run(MashupBatchApplication.class, args);
    }
}
