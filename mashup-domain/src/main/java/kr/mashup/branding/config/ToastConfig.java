package kr.mashup.branding.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ToastConfig {
    @Bean
    public RestTemplate toastRestTemplate() {
        return new RestTemplate();
    }
}
