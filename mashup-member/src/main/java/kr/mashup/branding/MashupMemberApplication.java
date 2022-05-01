package kr.mashup.branding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MashupMemberApplication {
	public static void main(String[] args) {
		System.setProperty("spring.config.name", "application,member");
		SpringApplication.run(MashupMemberApplication.class, args);
	}

}
