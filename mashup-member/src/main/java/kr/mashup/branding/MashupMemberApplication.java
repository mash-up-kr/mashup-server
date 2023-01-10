package kr.mashup.branding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MashupMemberApplication {
	public static void main(String[] args) {
		System.setProperty("spring.config.name", "application,member");
		SpringApplication.run(MashupMemberApplication.class, args);
	}

}
