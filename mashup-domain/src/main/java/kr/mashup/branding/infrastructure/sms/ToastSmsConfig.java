package kr.mashup.branding.infrastructure.sms;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import kr.mashup.branding.domain.notification.sms.SmsService;
import kr.mashup.branding.util.RequestHeaderInterceptor;

@Profile("sms")
@Configuration
public class ToastSmsConfig {

    @Value("${sms.toast.url}")
    private String toastUrl;

    @Value("${sms.toast.app-key}")
    private String appKey;

    @Value("${sms.toast.secret-key}")
    private String secretKey;

    @Bean
    public RestTemplate toastRestTemplate() {
        return new RestTemplateBuilder()
            .interceptors(
                new RequestHeaderInterceptor("X-Secret-Key", secretKey),
                new RequestHeaderInterceptor("Content-Type", MediaType.APPLICATION_JSON_VALUE))
            .build();
    }

    @Bean
    public SmsService toastSmsService() {
        return new ToastSmsService(
            toastUrl,
            appKey,
            toastRestTemplate()
        );
    }
}
