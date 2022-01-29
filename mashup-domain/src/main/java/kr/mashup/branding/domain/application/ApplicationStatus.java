package kr.mashup.branding.domain.application;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public enum ApplicationStatus {
    CREATED("생성됨. 임시저장 0회"),
    WRITING("작성중. 임시저장 1회 이상"),
    SUBMITTED("제출완료"),
    ;

    /**
     * 지원자에게 노출되는 지원서 상태 목록
     */
    private static final Set<ApplicationStatus> VALID_SET = new HashSet<>(Arrays.asList(WRITING, SUBMITTED));

    /**
     * @param description 상태에 대한 설명
     */
    ApplicationStatus(String description) {
    }

    public static Set<ApplicationStatus> validSet() {
        return VALID_SET;
    }

    ApplicationStatus update() {
        switch (this) {
            case CREATED:
            case WRITING:
                return WRITING;
            case SUBMITTED:
                throw new ApplicationAlreadySubmittedException();
        }
        // 컴파일 에러 방지
        throw new IllegalStateException();
    }

    ApplicationStatus submit() {
        switch (this) {
            case CREATED:
            case WRITING:
                return WRITING;
            case SUBMITTED:
                throw new ApplicationAlreadySubmittedException();
        }
        // 컴파일 에러 방지
        throw new IllegalStateException();
    }
}
