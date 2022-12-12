package kr.mashup.branding.config.fcm;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Configuration
public class FcmConfig {
    @Value("${fcm.secret}")
    private String secretKey;

    @Bean
    public FirebaseApp firebaseApp() throws IOException {
        final FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(new ByteArrayInputStream(secretKey.getBytes())))
                .build();
        return FirebaseApp.initializeApp(options, "mashup");
    }
}
