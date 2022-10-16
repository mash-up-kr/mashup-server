package kr.mashup.branding.facade.application;

import kr.mashup.branding.domain.application.Application;
import kr.mashup.branding.domain.application.ApplicationStatus;
import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.service.application.ApplicationService;
import kr.mashup.branding.service.application.ConfirmationService;
import kr.mashup.branding.service.generation.GenerationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.mashup.branding.domain.recruitmentschedule.RecruitmentScheduleEventName.RECRUITMENT_ENDED;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ConfirmationFacadeService {
    private final GenerationService generationService;
    private final ApplicationService applicationService;
    private final ConfirmationService confirmationService;

    /**
     * 리쿠르팅 마감 전 생성된 전체 지원서 중 임시 저장 중인 지원서를 조회하여 지원자 응답 변경
     */
    @Transactional
    public void updateToBeDeterminedToNotApplicable(Integer generationNumber) {
        final Generation generation = generationService.getByNumberOrThrow(generationNumber);
        final List<Application> applications
            = applicationService
            .getApplicationsByStatusAndEventName(generation, ApplicationStatus.WRITING, RECRUITMENT_ENDED);

        confirmationService
            .updateToBeDeterminedToNotApplicable(applications);
    }

    /**
     * 제출된 지원서 중 서류 결과가 합격인 지원서를 조회하여 지원자 응답 변경
     */
    @Transactional
    public void updateInterviewConfirmWaitingToRejected(Integer generationNumber) {
        final Generation generation = generationService.getByNumberOrThrow(generationNumber);
        final List<Application> applications
            = applicationService.getApplicationsByStatusAndEventName(generation, ApplicationStatus.SUBMITTED, RECRUITMENT_ENDED);

        confirmationService.updateInterviewConfirmWaitingToRejected(applications);
    }

    /**
     * 제출된 지원서 중 면접 결과가 합격인 지원서를 조회하여 지원자 응답 변경
     */
    @Transactional
    public void updateFinalConfirmWaitingToRejected(Integer generationNumber) {
        final Generation generation = generationService.getByNumberOrThrow(generationNumber);
        final List<Application> applications
            = applicationService.getApplicationsByStatusAndEventName(generation, ApplicationStatus.SUBMITTED, RECRUITMENT_ENDED);
        confirmationService.updateFinalConfirmWaitingToRejected(applications);
    }
}
