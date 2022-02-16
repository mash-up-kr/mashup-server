package kr.mashup.branding.domain;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException() {
        super();
    }

    public UnauthorizedException(String message, Exception e) {
        super(message, e);
    }
}
