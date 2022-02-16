package kr.mashup.branding.domain.application.form;

public class ApplicationFormAlreadyExistException extends RuntimeException {
    public ApplicationFormAlreadyExistException(String message) {
        super(message);
    }
}
