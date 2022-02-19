package kr.mashup.branding.domain.notification.sms;

public enum SmsNotificationStatus {
    CREATED("생성"),
    SUCCESS("성공"),
    FAILURE("실패"),
    ;

    SmsNotificationStatus(String description) {
    }
}
