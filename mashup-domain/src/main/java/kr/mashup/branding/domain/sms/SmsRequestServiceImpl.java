package kr.mashup.branding.domain.sms;

import kr.mashup.branding.domain.sms.dto.ToastSmsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
class SmsRequestServiceImpl implements SmsRequestService{

    private final SmsRequestRepository smsRequestRepository;
    private final SmsRequestGroupService smsRequestGroupService;

    @Override
    public void saveAll(List<SmsRequest> requests){
        smsRequestRepository.saveAll(requests);
    }

    @Override
    public SmsRequest createSmsRequest(SmsRequestGroup requestGroup, SmsRequestVo smsRequestVo) {
        return SmsRequest.builder()
                .smsRequestGroup(requestGroup)
                .userId(smsRequestVo.getUserId())
                .username(smsRequestVo.getUsername())
                .phoneNumber(smsRequestVo.getPhoneNumber())
                .build();
    }

    @Override
    public List<SmsRequest> getRequests(Long groupId) {
        SmsRequestGroup requestGroup = smsRequestGroupService.getRequestGroup(groupId);
        return requestGroup.getSmsRequests();
    }

    @Override
    public List<SmsRequest> getFailedRequests(Long groupId) {
        SmsRequestGroup requestGroup = smsRequestGroupService.getRequestGroup(groupId);
        return smsRequestRepository.findAllBySmsRequestGroupIdAndStatus(requestGroup, SmsRequestStatus.FAIL);
    }

    @Override
    public void markRequests(ToastSmsResponse toastSmsResponse, SmsRequestGroup smsRequestGroup) throws Exception {
        List<SmsRequest> smsRequests = smsRequestGroup.getSmsRequests();
        if (!toastSmsResponse.getHeader().getIsSuccessful()) {
            smsRequests.forEach(this::markAsFail);
            throw new Exception(toastSmsResponse.getHeader().getResultMessage());
        }

        Map<Long, SmsRequest> requestMap = smsRequests.stream().collect(Collectors.toMap(SmsRequest::getId, Function.identity()));

        toastSmsResponse.getBody().getData().getSendResultList().forEach(
                toastSendResult -> {
                    SmsRequest smsRequest = requestMap.get(Long.parseLong(toastSendResult.getRecipientGroupingKey()));
                    if (toastSendResult.getResultCode() == 0) {
                        markAsFail(smsRequest);
                    } else {
                        markAsSuccess(smsRequest);
                    }
                }
        );
    }

    private void markAsSuccess(SmsRequest smsRequest) {
        smsRequest.setStatus(SmsRequestStatus.SUCCESS);
    }
    private void markAsFail(SmsRequest smsRequest) {
        smsRequest.setStatus(SmsRequestStatus.FAIL);
    }

}
