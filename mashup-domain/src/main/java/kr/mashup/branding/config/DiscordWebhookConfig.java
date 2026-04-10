package kr.mashup.branding.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class DiscordWebhookConfig {

    @Bean
    public RestTemplate discordRestTemplate() {
        return new RestTemplate();
    }
}
