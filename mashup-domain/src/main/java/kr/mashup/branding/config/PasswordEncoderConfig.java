package kr.mashup.branding.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordEncoderConfig {
    @Primary
    @Bean("defaultPasswordEncoder")
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean("fourTimesRoundPasswordEncoder")
    public PasswordEncoder fourTimesRoundPasswordEncoder() {
        return new BCryptPasswordEncoder(4);
    }
}
