package kr.mashup.branding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MashupBatchApplication {
    public static void main(String[] args) {
        System.exit(SpringApplication.exit(
            SpringApplication.run(MashupBatchApplication.class, args)
        ));
    }
}
