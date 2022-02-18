package kr.mashup.branding;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MashupBatchApplication {
    public static void main(String[] args) {
        System.setProperty("spring.config.name", "application,batch");
        List<String> argList = new ArrayList<>(Arrays.asList(args));
        argList.add("startTime=" + LocalDateTime.now());
        SpringApplication.run(MashupBatchApplication.class, argList.toArray(new String[0]));
    }
}
