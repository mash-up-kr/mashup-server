package kr.mashup.branding.infrastructure.discord;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class DiscordWebhookService {

    private final RestTemplate discordRestTemplate;

    @Value("${discord.webhook-url:}")
    private String webhookUrl;

    public void send(String content) {
        if (!StringUtils.hasText(webhookUrl)) return;
        Map<String, String> body = Map.of("content", content);
        discordRestTemplate.postForEntity(webhookUrl, body, String.class);
    }
}
