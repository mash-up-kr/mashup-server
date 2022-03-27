package kr.mashup.branding.domain.application.result;

import lombok.Value;

import java.time.LocalDateTime;

@Value(staticConstructor = "of")
public class UpdateApplicationResultVo {
    Long applicationId;
    ApplicationScreeningStatus screeningStatus;
    ApplicationInterviewStatus interviewStatus;
    LocalDateTime interviewStartedAt;
    LocalDateTime interviewEndedAt;
    String interviewGuideLink;

    public static UpdateApplicationResultVo notRated(Long applicationId) {
        return UpdateApplicationResultVo.of(
            applicationId,
            ApplicationScreeningStatus.NOT_RATED,
            ApplicationInterviewStatus.NOT_RATED,
            null,
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
            null,
            null
        );
    }

    public static UpdateApplicationResultVo screeningToBeDetermined(Long applicationId) {
        return UpdateApplicationResultVo.of(
            applicationId,
            ApplicationScreeningStatus.TO_BE_DETERMINED,
            ApplicationInterviewStatus.NOT_RATED,
            null,
            null,
            null
        );
    }

    public static UpdateApplicationResultVo screeningPassed(
        Long applicationId,
        LocalDateTime interviewStartedAt,
        LocalDateTime interviewEndedAt,
        String interviewGuideLink
    ) {
        return UpdateApplicationResultVo.of(
            applicationId,
            ApplicationScreeningStatus.PASSED,
            ApplicationInterviewStatus.NOT_RATED,
            interviewStartedAt,
            interviewEndedAt,
            interviewGuideLink
        );
    }

    public static UpdateApplicationResultVo interviewFailed(Long applicationId) {
        return UpdateApplicationResultVo.of(
            applicationId,
            ApplicationScreeningStatus.PASSED,
            ApplicationInterviewStatus.FAILED,
            null,
            null,
            null
        );
    }

    public static UpdateApplicationResultVo interviewToBeDetermined(
        Long applicationId,
        LocalDateTime interviewStartedAt,
        LocalDateTime interviewEndedAt,
        String interviewGuideLink
    ) {
        return UpdateApplicationResultVo.of(
            applicationId,
            ApplicationScreeningStatus.PASSED,
            ApplicationInterviewStatus.TO_BE_DETERMINED,
            interviewStartedAt,
            interviewEndedAt,
            interviewGuideLink
        );
    }

    public static UpdateApplicationResultVo interviewPassed(Long applicationId) {
        return UpdateApplicationResultVo.of(
            applicationId,
            ApplicationScreeningStatus.PASSED,
            ApplicationInterviewStatus.PASSED,
            null,
            null,
            null
        );
    }
}
