package kr.mashup.branding.domain.adminmember.exception;

public class AdminMemberUsernameDuplicatedException extends RuntimeException {
    public AdminMemberUsernameDuplicatedException(String message) {
        super(message);
    }
}
