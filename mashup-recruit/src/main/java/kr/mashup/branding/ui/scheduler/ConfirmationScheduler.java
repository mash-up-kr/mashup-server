package kr.mashup.branding.ui.scheduler;

import kr.mashup.branding.facade.application.ConfirmationFacadeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class ConfirmationScheduler {
    private final ConfirmationFacadeService confirmationFacadeService;
    // TODO : Recruitment Schedule 대응 필요
    /**
     * 지원 마감시 임시저장인 지원서에 대한 지원자 응답 변경
     * TO_BE_DETERMINED -> NOT_APPLICABLE
     */
    @Scheduled(cron = "0 0 0 26 1 ?") // 2023-01-26 00:00:00
    public void updateConfirmationAtRecruitmentEnded() {
        log.info("[ConfirmationScheduler] updateConfirmationAtRecruitmentEnded >>> start");
        confirmationFacadeService.updateToBeDeterminedToNotApplicable(13);
        log.info("[ConfirmationScheduler] updateConfirmationAtRecruitmentEnded >>> end");
    }

    /**
     * 서류 발표 후 24시간 뒤 서류 합격이지만 응답 대기중인 지원서에 대한 지원자 응답 변경
     * INTERVIEW_CONFIRM_WAITING -> INTERVIEW_CONFIRM_REJECTED
     */
    @Scheduled(cron = "0 0 21 31 1 ?") // 2023-01-30 21:00:00 24시간 뒤인 01-31 21:00:00
    public void updateConfirmationAtScreeningEnded() {
        log.info("[ConfirmationScheduler] updateConfirmationAtScreeningEnded >>> start");
        confirmationFacadeService.updateInterviewConfirmWaitingToRejected(13);
        log.info("[ConfirmationScheduler] updateConfirmationAtScreeningEnded >>> end");
    }

    /**
     * 면접 발표 후 24시간 뒤 면접 합격이지만 응답 대기중인 지원서에 대한 지원자 응답 변경
     * FINAL_CONFIRM_WAITING -> FINAL_CONFIRM_REJECTED
     */
    @Scheduled(cron = "0 0 21 8 2 ?") // 2023-02-07 21:00:00 24시간 뒤인 02-08 21:00:00
    public void updateConfirmationAtInterviewEnded() {
        log.info("[ConfirmationScheduler] updateConfirmationAtInterviewEnded >>> start");
        confirmationFacadeService.updateFinalConfirmWaitingToRejected(13);
        log.info("[ConfirmationScheduler] updateConfirmationAtInterviewEnded >>> end");
    }
}
