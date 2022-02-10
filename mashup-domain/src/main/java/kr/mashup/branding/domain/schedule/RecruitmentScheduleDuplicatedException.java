package kr.mashup.branding.domain.schedule;

public class RecruitmentScheduleDuplicatedException extends RuntimeException {
    public RecruitmentScheduleDuplicatedException(String message) {
        super(message);
    }
}
