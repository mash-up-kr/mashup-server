package kr.mashup.branding.ui.application.vo;

import java.time.LocalDateTime;

import kr.mashup.branding.domain.application.result.UpdateApplicationResultVo;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UpdateApplicationResultRequest {
    private ApplicationResultStatus applicationResultStatus;
    private LocalDateTime interviewStartedAt;
    private LocalDateTime interviewEndedAt;
    private String interviewGuideLink;

    public UpdateApplicationResultVo toVo(Long applicationId){
        return toUpdateApplicationResultVo(
            applicationId,
            applicationResultStatus,
            interviewStartedAt,
            interviewEndedAt,
            interviewGuideLink
        );
    }

    private UpdateApplicationResultVo toUpdateApplicationResultVo(
        Long applicationId,
        ApplicationResultStatus applicationResultStatus,
        LocalDateTime interviewStartedAt,
        LocalDateTime interviewEndedAt,
        String interviewGuideLink
    ) {
        switch (applicationResultStatus) {
            case NOT_RATED:
                return UpdateApplicationResultVo.notRated(applicationId);
            case SCREENING_FAILED:
                return UpdateApplicationResultVo.screeningFailed(applicationId);
            case SCREENING_TO_BE_DETERMINED:
                return UpdateApplicationResultVo.screeningToBeDetermined(applicationId);
            case SCREENING_PASSED:
                return UpdateApplicationResultVo.screeningPassed(
                    applicationId,
                    interviewStartedAt,
                    interviewEndedAt,
                    interviewGuideLink
                );
            case INTERVIEW_FAILED:
                return UpdateApplicationResultVo.interviewFailed(applicationId);
            case INTERVIEW_TO_BE_DETERMINED:
                return UpdateApplicationResultVo.interviewToBeDetermined(
                    applicationId,
                    interviewStartedAt,
                    interviewEndedAt,
                    interviewGuideLink
                );
            case INTERVIEW_PASSED:
                return UpdateApplicationResultVo.interviewPassed(applicationId);
        }
        throw new IllegalStateException();
    }
}
