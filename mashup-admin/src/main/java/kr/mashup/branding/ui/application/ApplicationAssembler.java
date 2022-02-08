package kr.mashup.branding.ui.application;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import kr.mashup.branding.domain.application.Application;
import kr.mashup.branding.domain.application.result.UpdateApplicationResultVo;

@Component
public class ApplicationAssembler {
    ApplicationResponse toApplicationResponse(Application application) {
        return new ApplicationResponse(
            application.getApplicationId()
        );
    }

    public List<UpdateApplicationResultVo> toUpdateApplicationResultsVoList(UpdateApplicationResultsRequest request) {
        ApplicationResultStatusRequest applicationResultStatusRequest = request.getApplicationResultStatus();
        return request.getApplicationIds()
            .stream()
            .map(it -> toUpdateApplicationResultVo(it, applicationResultStatusRequest, null, null))
            .collect(Collectors.toList());
    }

    private UpdateApplicationResultVo toUpdateApplicationResultVo(
        Long applicationId,
        ApplicationResultStatusRequest applicationResultStatusRequest,
        LocalDateTime interviewStartedAt,
        LocalDateTime interviewEndedAt
    ) {
        switch (applicationResultStatusRequest) {
            case NOT_RATED:
                return UpdateApplicationResultVo.notRated(applicationId);
            case SCREENING_FAILED:
                return UpdateApplicationResultVo.screeningFailed(applicationId);
            case SCREENING_TBD:
                return UpdateApplicationResultVo.screeningTBD(applicationId);
            case SCREENING_PASSED:
                return UpdateApplicationResultVo.screeningPassed(
                    applicationId,
                    interviewStartedAt,
                    interviewEndedAt
                );
            case INTERVIEW_FAILED:
                return UpdateApplicationResultVo.interviewFailed(applicationId);
            case INTERVIEW_TBD:
                return UpdateApplicationResultVo.interviewTBD(
                    applicationId,
                    interviewStartedAt,
                    interviewEndedAt
                );
            case INTERVIEW_PASSED:
                return UpdateApplicationResultVo.interviewPassed(applicationId);
        }
        throw new IllegalStateException();
    }

    UpdateApplicationResultVo toUpdateApplicationResultVo(
        Long applicationId,
        UpdateApplicationResultRequest request
    ) {
        return toUpdateApplicationResultVo(
            applicationId,
            request.getApplicationResultStatus(),
            request.getInterviewStartedAt(),
            request.getInterviewEndedAt()
        );
    }
}
