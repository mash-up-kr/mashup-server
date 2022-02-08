package kr.mashup.branding.ui.application;

import java.time.LocalDateTime;

import kr.mashup.branding.domain.MashupSchedule;
import kr.mashup.branding.domain.application.ApplicationStatus;
import kr.mashup.branding.domain.application.result.ApplicationInterviewStatus;
import kr.mashup.branding.domain.application.result.ApplicationScreeningStatus;

/**
 * 지원자가 보는 지원 현황 > 지원 상태
 */
public enum ApplicationStatusResponse {
    WRITING("임시저장"),
    SUBMITTED("제출완료"),
    SCREENING_FAILED("서류불합격"),
    SCREENING_PASSED("서류합격"),
    INTERVIEW_FAILED("면접불합격"),
    INTERVIEW_PASSED("최종합격"),
    ;

    ApplicationStatusResponse(String description) {
    }

    public static ApplicationStatusResponse of(
        ApplicationStatus applicationStatus,
        ApplicationScreeningStatus screeningStatus,
        ApplicationInterviewStatus interviewStatus
    ) {
        // TODO: 13기 생기면 기수별로 일정 관리해야함
        LocalDateTime now = LocalDateTime.now();
        if (!MashupSchedule.canAnnounceScreeningResult(now)) {
            return ApplicationStatusResponse.from(applicationStatus);
        }
        if (!MashupSchedule.canAnnounceInterviewResult(now)) {
            return ApplicationStatusResponse.fromScreening(screeningStatus);
        }
        return ApplicationStatusResponse.fromInterview(interviewStatus);
    }

    private static ApplicationStatusResponse from(ApplicationStatus status) {
        if (status.isSubmitted()) {
            return SUBMITTED;
        }
        return WRITING;
    }

    private static ApplicationStatusResponse fromScreening(ApplicationScreeningStatus screeningStatus) {
        if (screeningStatus == ApplicationScreeningStatus.PASSED) {
            return SCREENING_PASSED;
        }
        return SCREENING_FAILED;
    }

    private static ApplicationStatusResponse fromInterview(ApplicationInterviewStatus interviewStatus) {
        if (interviewStatus == ApplicationInterviewStatus.PASSED) {
            return INTERVIEW_PASSED;
        }
        return INTERVIEW_FAILED;
    }
}
