package kr.mashup.branding.config.security;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

// 클래스 위치 코멘트
public class TokenMissingException extends UsernameNotFoundException {
    public TokenMissingException(String message) {
        super(message);
    }

    public TokenMissingException(String message, Throwable cause) {
        super(message, cause);
    }
}
