package kr.mashup.branding.domain.application.result;

import java.time.LocalDateTime;

import lombok.Value;

@Value(staticConstructor = "of")
public class UpdateApplicationResultVo {
    Long applicationId;
    ApplicationScreeningStatus screeningStatus;
    ApplicationInterviewStatus interviewStatus;
    LocalDateTime interviewStartedAt;
    LocalDateTime interviewEndedAt;

    public static UpdateApplicationResultVo notRated(Long applicationId) {
        return UpdateApplicationResultVo.of(
            applicationId,
            ApplicationScreeningStatus.NOT_RATED,
            ApplicationInterviewStatus.NOT_RATED,
            null,
            null
        );
    }

    public static UpdateApplicationResultVo screeningFailed(Long applicationId) {
        return UpdateApplicationResultVo.of(
            applicationId,
            ApplicationScreeningStatus.FAILED,
            ApplicationInterviewStatus.NOT_APPLICABLE,
            null,
            null
        );
    }

    public static UpdateApplicationResultVo screeningTBD(Long applicationId) {
        return UpdateApplicationResultVo.of(
            applicationId,
            ApplicationScreeningStatus.TBD,
            ApplicationInterviewStatus.NOT_RATED,
            null,
            null
        );
    }

    public static UpdateApplicationResultVo screeningPassed(
        Long applicationId,
        LocalDateTime interviewStartedAt,
        LocalDateTime interviewEndedAt
    ) {
        return UpdateApplicationResultVo.of(
            applicationId,
            ApplicationScreeningStatus.PASSED,
            ApplicationInterviewStatus.NOT_RATED,
            interviewStartedAt,
            interviewEndedAt
        );
    }

    public static UpdateApplicationResultVo interviewFailed(Long applicationId) {
        return UpdateApplicationResultVo.of(
            applicationId,
            ApplicationScreeningStatus.PASSED,
            ApplicationInterviewStatus.FAILED,
            null,
            null
        );
    }

    public static UpdateApplicationResultVo interviewTBD(
        Long applicationId,
        LocalDateTime interviewStartedAt,
        LocalDateTime interviewEndedAt
    ) {
        return UpdateApplicationResultVo.of(
            applicationId,
            ApplicationScreeningStatus.PASSED,
            ApplicationInterviewStatus.TBD,
            interviewStartedAt,
            interviewEndedAt
        );
    }

    public static UpdateApplicationResultVo interviewPassed(Long applicationId) {
        return UpdateApplicationResultVo.of(
            applicationId,
            ApplicationScreeningStatus.PASSED,
            ApplicationInterviewStatus.PASSED,
            null,
            null
        );
    }
}
