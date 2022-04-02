package kr.mashup.branding.facade.application;

import kr.mashup.branding.domain.application.confirmation.ConfirmationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConfirmationFacadeServiceImpl implements ConfirmationFacadeService {
    private final ConfirmationService confirmationService;

    /**
     * 리쿠르팅 마감 전 생성된 전체 지원서 중 임시 저장 중인 지원서 조회하여 지원자 응답 변경
     */
    @Override
    public void updateToBeDeterminedToNotApplicable() {
        confirmationService.updateToBeDeterminedToNotApplicable();
    }
}
