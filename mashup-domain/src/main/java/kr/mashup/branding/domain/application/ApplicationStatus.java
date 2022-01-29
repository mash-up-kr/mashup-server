package kr.mashup.branding.domain.application;

public enum ApplicationStatus {
    CREATED("생성됨. 임시저장 0회"),
    WRITING("작성중. 임시저장 1회 이상"),
    SUBMITTED("제출완료"),
    ;

    private final String description;

    ApplicationStatus(String description) {
        this.description = description;
    }
}
