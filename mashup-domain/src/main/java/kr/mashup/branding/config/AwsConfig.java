package kr.mashup.branding.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsConfig {

    @Value("${aws.access-key}")
    private String ACCESS_KEY;

    @Value("${aws.secret-key}")
    private String SECRET_KEY;

    @Bean
    public AWSCredentials AWSCredentials() {
        AWSCredentials awsCredentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
        return awsCredentials;
    }
}
