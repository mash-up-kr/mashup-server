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

    /**
     * 지원 마감시 임시저장인 지원서에 대한 지원자 응답 변경
     * TO_BE_DETERMINED -> NOT_APPLICABLE
     */
    @Scheduled(cron = "0 0 0 30 3 ?") // 2022-03-30 00:00:00
    public void updateConfirmationAtRecruitmentEnded() {
        log.info("[ConfirmationScheduler] updateConfirmationAtRecruitmentEnded >>> start");
        confirmationFacadeService.updateToBeDeterminedToNotApplicable();
        log.info("[ConfirmationScheduler] updateConfirmationAtRecruitmentEnded >>> end");
    }
}
