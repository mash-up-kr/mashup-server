package kr.mashup.branding.ui.application;

import kr.mashup.branding.domain.application.ApplicationQueryRequest;
import kr.mashup.branding.domain.application.confirmation.ApplicantConfirmationStatus;
import kr.mashup.branding.domain.application.result.ApplicationInterviewStatus;
import kr.mashup.branding.domain.application.result.ApplicationScreeningStatus;
import kr.mashup.branding.domain.application.result.UpdateApplicationResultVo;
import kr.mashup.branding.ui.application.vo.ApplicationResultStatus;
import kr.mashup.branding.ui.application.vo.UpdateApplicationResultsRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ApplicationAssembler {


    public List<UpdateApplicationResultVo> toUpdateApplicationResultsVoList(UpdateApplicationResultsRequest request) {
        ApplicationResultStatus applicationResultStatus = request.getApplicationResultStatus();
        return request.getApplicationIds()
            .stream()
            .map(it -> toUpdateApplicationResultVo(it, applicationResultStatus, null, null, null))
            .collect(Collectors.toList());
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

    ApplicationQueryRequest toApplicationQueryRequest(
        Integer generationNumber,
        String searchWord,
        Long teamId,
        ApplicantConfirmationStatus confirmStatus,
        ApplicationResultStatus resultStatus,
        Boolean isShowAll,
        Pageable pageable
    ) {
        if(isShowAll == null) isShowAll = false;

        return ApplicationQueryRequest.of(
            generationNumber,
            searchWord,
            teamId,
            confirmStatus,
            toApplicationScreeningStatus(resultStatus),
            toApplicationInterviewStatus(resultStatus),
            isShowAll,
            pageable
        );
    }

    private ApplicationScreeningStatus toApplicationScreeningStatus(ApplicationResultStatus resultStatus) {
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

    private ApplicationInterviewStatus toApplicationInterviewStatus(ApplicationResultStatus resultStatus) {
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
