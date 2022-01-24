package kr.mashup.branding.domain.sms;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
class SmsRequestServiceImpl implements SmsRequestService{

    private final SmsRequestRepository smsRequestRepository;

    @Override
    public SmsRequest createSmsRequest(SmsRequestVo smsRequestVo) {
        return SmsRequest.builder()
                .groupId(smsRequestVo.getGroupId())
                .userId(smsRequestVo.getUserId())
                .username(smsRequestVo.getUsername())
                .content(smsRequestVo.getContent())
                .phoneNumber(smsRequestVo.getPhoneNumber())
                .build();
    }

    @Override
    public List<SmsRequest> getRequests(Long groupId) {
        return smsRequestRepository.findAllByGroupId(groupId);
    }

    @Override
    public List<SmsRequest> getFailedRequests(Long groupId) {
        return smsRequestRepository.findAllByGroupIdAndIsSuccessFalse(groupId);
    }

    @Override
    public void markAsSuccess(SmsRequest smsRequest) {
        smsRequest.setIsSuccess(true);
    }

    @Override
    public void markAsFail(SmsRequest smsRequest) {
        smsRequest.setIsSuccess(false);
    }

}
