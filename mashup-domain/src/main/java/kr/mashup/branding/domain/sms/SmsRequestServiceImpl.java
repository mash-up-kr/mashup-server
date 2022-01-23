package kr.mashup.branding.domain.sms;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
class SmsRequestServiceImpl {

    private final SmsRequestRepository smsRequestRepository;

    SmsRequest createSmsRequest(SmsRequestVo smsRequestVo) {
        return SmsRequest.builder()
                .groupId(smsRequestVo.getGroupId())
                .userId(smsRequestVo.getUserId())
                .username(smsRequestVo.getUsername())
                .content(smsRequestVo.getContent())
                .phoneNumber(smsRequestVo.getPhoneNumber())
                .build();
    }

    List<SmsRequest> getRequests(Long groupId) {
        return smsRequestRepository.findAllByGroupId(groupId);
    }

    List<SmsRequest> getFailedRequests(Long groupId) {
        return smsRequestRepository.findAllByGroupIdAndIsSuccessFalse(groupId);
    }

    void markAsSuccess(SmsRequest smsRequest) {
        smsRequest.setIsSuccess(true);
    }

    void markAsFail(SmsRequest smsRequest) {
        smsRequest.setIsSuccess(false);
    }

}
