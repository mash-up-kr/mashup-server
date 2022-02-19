package kr.mashup.branding.domain.notification;

public enum NotificationStatus {
    CREATED("생성됨"),
    UNKNOWN("확인 필요"),
    IN_PROGRESS("전송 중"),
    SUCCESS("성공"),
    FAILURE("실패"),
    ;

    NotificationStatus(String description) {
    }
}
