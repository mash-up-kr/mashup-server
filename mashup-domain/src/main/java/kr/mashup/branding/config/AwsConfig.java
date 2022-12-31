package kr.mashup.branding.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsConfig {

    @Value("${aws.ses.access-key}")
    private String ACCESS_KEY;

    @Value("${aws.ses.secret-key}")
    private String SECRET_KEY;

    @Bean
    public AWSCredentials AWSCredentials() {
        return new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
    }
}
