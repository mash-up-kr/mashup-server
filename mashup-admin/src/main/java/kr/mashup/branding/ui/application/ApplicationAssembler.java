package kr.mashup.branding.ui.application;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import kr.mashup.branding.domain.application.Application;
import kr.mashup.branding.domain.application.ApplicationQueryVo;
import kr.mashup.branding.domain.application.confirmation.ApplicantConfirmationStatus;
import kr.mashup.branding.domain.application.result.ApplicationInterviewStatus;
import kr.mashup.branding.domain.application.result.ApplicationScreeningStatus;
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
            case SCREENING_TO_BE_DETERMINED:
                return UpdateApplicationResultVo.screeningToBeDetermined(applicationId);
            case SCREENING_PASSED:
                return UpdateApplicationResultVo.screeningPassed(
                    applicationId,
                    interviewStartedAt,
                    interviewEndedAt
                );
            case INTERVIEW_FAILED:
                return UpdateApplicationResultVo.interviewFailed(applicationId);
            case INTERVIEW_TO_BE_DETERMINED:
                return UpdateApplicationResultVo.interviewToBeDetermined(
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

    ApplicationQueryVo toApplicationQueryVo(
        String searchWord,
        Long teamId,
        ApplicantConfirmationStatus confirmStatus,
        ApplicationResultStatusRequest resultStatus,
        Pageable pageable
    ) {
        return ApplicationQueryVo.of(
            searchWord,
            teamId,
            confirmStatus,
            toApplicationScreeningStatus(resultStatus),
            toApplicationInterviewStatus(resultStatus),
            pageable
        );
    }

    private ApplicationScreeningStatus toApplicationScreeningStatus(ApplicationResultStatusRequest resultStatus) {
        if (resultStatus == null) {
            return null;
        }
        switch (resultStatus) {
            case NOT_RATED:
                return ApplicationScreeningStatus.NOT_RATED;
            case SCREENING_FAILED:
                return ApplicationScreeningStatus.FAILED;
            case SCREENING_TO_BE_DETERMINED:
                return ApplicationScreeningStatus.TO_BE_DETERMINED;
            case SCREENING_PASSED:
                return ApplicationScreeningStatus.PASSED;
            case INTERVIEW_FAILED:
            case INTERVIEW_TO_BE_DETERMINED:
            case INTERVIEW_PASSED:
                return null;
        }
        throw new IllegalStateException();
    }

    private ApplicationInterviewStatus toApplicationInterviewStatus(ApplicationResultStatusRequest resultStatus) {
        if (resultStatus == null) {
            return null;
        }
        switch (resultStatus) {
            case NOT_RATED:
            case SCREENING_FAILED:
            case SCREENING_TO_BE_DETERMINED:
            case SCREENING_PASSED:
                return null;
            case INTERVIEW_FAILED:
                return ApplicationInterviewStatus.FAILED;
            case INTERVIEW_TO_BE_DETERMINED:
                return ApplicationInterviewStatus.TO_BE_DETERMINED;
            case INTERVIEW_PASSED:
                return ApplicationInterviewStatus.PASSED;
        }
        throw new IllegalStateException();
    }
}
