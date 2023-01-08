package kr.mashup.branding.service.application;

import kr.mashup.branding.domain.application.Application;
import kr.mashup.branding.domain.application.ApplicationStatus;
import kr.mashup.branding.domain.application.confirmation.ApplicantConfirmationStatus;
import kr.mashup.branding.domain.application.result.ApplicationInterviewStatus;
import kr.mashup.branding.domain.application.result.ApplicationScreeningStatus;
import kr.mashup.branding.domain.recruitmentschedule.RecruitmentScheduleEventName;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ConfirmationService {
    private static final RecruitmentScheduleEventName RECRUITMENT_ENDED =
        RecruitmentScheduleEventName.RECRUITMENT_ENDED;

    @Transactional
    public void updateToBeDeterminedToNotApplicable(final List<Application> applications) {

        applications
            .stream()
            .filter(application ->
                application.getConfirmation().getStatus().equals(ApplicantConfirmationStatus.TO_BE_DETERMINED)
            )
            .forEach(application -> {
                    application.updateConfirmationStatus(ApplicantConfirmationStatus.NOT_APPLICABLE);
                    log.info("[updateToBeDeterminedToNotApplicable] applicationId: " + application.getApplicationId());
                }
            );
    }

    @Transactional
    public void updateInterviewConfirmWaitingToRejected(final List<Application> applications) {

        applications
            .stream()
            .filter(application ->
                application.getApplicationResult().getScreeningStatus().equals(ApplicationScreeningStatus.PASSED) &&
                    application.getConfirmation().getStatus().equals(ApplicantConfirmationStatus.INTERVIEW_CONFIRM_WAITING)
            )
            .forEach(application -> {
                    application.updateConfirmationStatus(ApplicantConfirmationStatus.INTERVIEW_CONFIRM_REJECTED);
                    log.info("[updateInterviewConfirmWaitingToRejected] applicationId: " + application.getApplicationId());
                }
            );
    }

    @Transactional
    public void updateFinalConfirmWaitingToRejected(final List<Application> applications) {

        applications
            .stream()
            .filter(application ->
                application.getApplicationResult().getInterviewStatus().equals(ApplicationInterviewStatus.PASSED) &&
                    application.getConfirmation().getStatus().equals(ApplicantConfirmationStatus.FINAL_CONFIRM_WAITING)
            )
            .forEach(application -> {
                    application.updateConfirmationStatus(ApplicantConfirmationStatus.FINAL_CONFIRM_REJECTED);
                    log.info("[updateFinalConfirmWaitingToRejected] applicationId: " + application.getApplicationId());
                }
            );
    }
}
