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
    public List<SmsRequest> createAndSaveAll(SmsRequestGroup requestGroup, List<SmsRequestVo> smsRequestVoList) {
        List<SmsRequest> smsRequests = smsRequestVoList.stream().map(smsRequestVo ->
                SmsRequest.builder()
                        .smsRequestGroup(requestGroup)
                        .toastKey(smsRequestVo.getToastKey())
                        .userId(smsRequestVo.getUserId())
                        .username(smsRequestVo.getUsername())
                        .phoneNumber(smsRequestVo.getPhoneNumber())
                        .build()
        ).collect(Collectors.toList());
        return smsRequestRepository.saveAll(smsRequests);
    }

    @Override
    public List<SmsRequest> getRequests(Long groupId) {
        SmsRequestGroup requestGroup = smsRequestGroupService.getRequestGroup(groupId);
        return requestGroup.getSmsRequests();
    }

    @Override
    public List<SmsRequest> getFailedRequests(Long groupId) {
        SmsRequestGroup requestGroup = smsRequestGroupService.getRequestGroup(groupId);
        return smsRequestRepository.findAllBySmsRequestGroupAndStatus(requestGroup, SmsRequestStatus.FAIL);
    }

    @Override
    public void markRequestsWithToastResponse(ToastSmsResponse toastSmsResponse, SmsRequestGroup smsRequestGroup) {
        List<SmsRequest> smsRequests = smsRequestGroup.getSmsRequests();
        Map<Long, SmsRequest> requestMap = smsRequests.stream().collect(Collectors.toMap(SmsRequest::getSmsRequestId, Function.identity()));
        toastSmsResponse.getBody().getData().getSendResultList().forEach(
                toastSendResult -> {
                    SmsRequest smsRequest = requestMap.get(Long.parseLong(toastSendResult.getRecipientGroupingKey()));
                    if (toastSendResult.getResultCode() == 0) {
                        smsRequest.markAsSuccess();
                    } else {
                        smsRequest.markAsFail();
                    }
                }
        );
    }
}
