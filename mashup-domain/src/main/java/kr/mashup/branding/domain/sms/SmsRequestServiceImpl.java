package kr.mashup.branding.domain.sms;

import kr.mashup.branding.domain.sms.dto.ToastSmsResponse;
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
    public void saveAll(List<SmsRequest> requests){
        smsRequestRepository.saveAll(requests);
    }

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
    public void markRequests(ToastSmsResponse toastSmsResponse, List<SmsRequest> requests) {
        if (!toastSmsResponse.getHeader().getIsSuccessful()) {
            requests.forEach(this::markAsFail);
            return;
        }

        //TODO: 발송 성공, 실패 request 분리 ~ 01.29

    }

    private void markAsSuccess(SmsRequest smsRequest) {
        smsRequest.setIsSuccess(true);
    }

    private void markAsFail(SmsRequest smsRequest) {
        smsRequest.setIsSuccess(false);
    }

}
