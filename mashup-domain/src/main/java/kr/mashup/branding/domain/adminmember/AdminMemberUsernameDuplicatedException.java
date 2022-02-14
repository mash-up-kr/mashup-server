package kr.mashup.branding.domain.adminmember;

public class AdminMemberUsernameDuplicatedException extends RuntimeException {
    public AdminMemberUsernameDuplicatedException(String message) {
        super(message);
    }
}
