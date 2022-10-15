package kr.mashup.branding.facade.application;

import kr.mashup.branding.service.application.ConfirmationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConfirmationFacadeService {
    private final ConfirmationService confirmationService;

    /**
     * 리쿠르팅 마감 전 생성된 전체 지원서 중 임시 저장 중인 지원서를 조회하여 지원자 응답 변경
     */
    public void updateToBeDeterminedToNotApplicable() {
        confirmationService.updateToBeDeterminedToNotApplicable();
    }

    /**
     * 제출된 지원서 중 서류 결과가 합격인 지원서를 조회하여 지원자 응답 변경
     */
    public void updateInterviewConfirmWaitingToRejected() {
        confirmationService.updateInterviewConfirmWaitingToRejected();
    }

    /**
     * 제출된 지원서 중 면접 결과가 합격인 지원서를 조회하여 지원자 응답 변경
     */
    public void updateFinalConfirmWaitingToRejected() {
        confirmationService.updateFinalConfirmWaitingToRejected();
    }
}
