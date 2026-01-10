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
    @Scheduled(cron = "0 0 0 12 2 ?") // 서류 마감 26년 2월11일 -> 2월 12일 00시에 변경
    public void updateConfirmationAtRecruitmentEnded() {
        log.info("[ConfirmationScheduler] updateConfirmationAtRecruitmentEnded >>> start");
        confirmationFacadeService.updateToBeDeterminedToNotApplicable(16);
        log.info("[ConfirmationScheduler] updateConfirmationAtRecruitmentEnded >>> end");
    }

    /**
     * 서류 발표 후 24시간 뒤 서류 합격이지만 응답 대기중인 지원서에 대한 지원자 응답 변경
     * INTERVIEW_CONFIRM_WAITING -> INTERVIEW_CONFIRM_REJECTED
     */
    @Scheduled(cron = "0 0 00 20 2 ?") // 서류 발표 26-02-18일 다음 날이 끝나는 2월 20일 00시
    public void updateConfirmationAtScreeningEnded() {
        log.info("[ConfirmationScheduler] updateConfirmationAtScreeningEnded >>> start");
        confirmationFacadeService.updateInterviewConfirmWaitingToRejected(16);
        log.info("[ConfirmationScheduler] updateConfirmationAtScreeningEnded >>> end");
    }

    /**
     * 면접 발표 후 24시간 뒤 면접 합격이지만 응답 대기중인 지원서에 대한 지원자 응답 변경
     * 15기 한정 24시간이 아닌 23:59:59 로 변경
     * FINAL_CONFIRM_WAITING -> FINAL_CONFIRM_REJECTED
     */
    @Scheduled(cron = "0 0 00 28 2 ?") // 26-02-26 최종 발표 하고 2월 28일 00시
    public void updateConfirmationAtInterviewEnded() {
        log.info("[ConfirmationScheduler] updateConfirmationAtInterviewEnded >>> start");
        confirmationFacadeService.updateFinalConfirmWaitingToRejected(16);
        log.info("[ConfirmationScheduler] updateConfirmationAtInterviewEnded >>> end");
    }
}
