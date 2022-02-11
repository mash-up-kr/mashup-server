package kr.mashup.branding.config.security;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class TokenMissingException extends UsernameNotFoundException {
    public TokenMissingException(String msg) {
        super(msg);
    }

    public TokenMissingException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
