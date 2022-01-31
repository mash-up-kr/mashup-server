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
class SmsRequestServiceImpl implements SmsRequestService {

    private final SmsRequestRepository smsRequestRepository;
    private final SmsRequestGroupService smsRequestGroupService;

    @Override
    public List<SmsRequest> createAndSaveAll(SmsRequestGroup requestGroup, List<SmsRequestVo> smsRequestVoList) {
        List<SmsRequest> smsRequests = smsRequestVoList.stream().map(smsRequestVo ->
                SmsRequest.of(
                        requestGroup,
                        smsRequestVo.getApplicantId(),
                        smsRequestVo.getApplicantName(),
                        smsRequestVo.getPhoneNumber()
                )
        ).collect(Collectors.toList());
        return smsRequestRepository.saveAll(smsRequests);
    }

    @Override
    public List<SmsRequest> getRequests(Long smsRequestGroupId) {
        SmsRequestGroup requestGroup = smsRequestGroupService.getRequestGroup(smsRequestGroupId);
        return requestGroup.getSmsRequests();
    }

    @Override
    public List<SmsRequest> getFailedRequests(Long smsRequestGroupId) {
        return smsRequestRepository.findBySmsRequestGroup_smsRequestGroupIdAndStatus(smsRequestGroupId, SmsRequestStatus.FAIL);
    }

    @Override
    public void markRequestsWithToastResponse(ToastSmsResponse toastSmsResponse, SmsRequestGroup smsRequestGroup) {
        List<SmsRequest> smsRequests = smsRequestGroup.getSmsRequests();
        Map<String, SmsRequest> requestMap = smsRequests.stream().collect(Collectors.toMap(SmsRequest::getSmsSendKey, Function.identity()));
        toastSmsResponse.getBody().getData().getSendResultList().forEach(
                toastSendResult -> {
                    SmsRequest smsRequest = requestMap.get(toastSendResult.getRecipientGroupingKey());
                    if (toastSendResult.getResultCode() == 0) {
                        smsRequest.markAsSuccess();
                    } else {
                        smsRequest.markAsFail();
                    }
                }
        );
    }

    @Override
    public void updateSmsSendKey(SmsRequest smsRequest, String smsSendKey) {
        smsRequest.updateSmsSendKey(smsSendKey);
    }
}
