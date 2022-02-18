package kr.mashup.branding.facade.notification.sms;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import kr.mashup.branding.domain.adminmember.AdminMemberService;
import kr.mashup.branding.domain.notification.sms.SmsRequest;
import kr.mashup.branding.domain.notification.sms.SmsRequestService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SmsRequestFacadeServiceImpl implements SmsRequestFacadeService {
    private final SmsRequestService smsRequestService;
    private final AdminMemberService adminMemberService;

    /**
     * 지원자 1명에 대한 문자 발송 이력 조회
     * @param adminMemberId 어드민 멤버 식별자
     * @param applicantId 지원자 식별자
     * @return 지원자에게 발송한 문자 발송 이력
     */
    @Override
    public List<SmsRequest> getSmsRequests(Long adminMemberId, Long applicantId) {
        Assert.notNull(adminMemberId, "'adminMemberId' must not be null");
        return smsRequestService.getSmsRequestsByApplicantId(applicantId);
    }
}
