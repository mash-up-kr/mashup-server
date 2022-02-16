package kr.mashup.branding.domain.application.form;

public class ApplicationFormModificationNotAllowedException extends RuntimeException {
    public ApplicationFormModificationNotAllowedException(String message) {
        super(message);
    }
}
