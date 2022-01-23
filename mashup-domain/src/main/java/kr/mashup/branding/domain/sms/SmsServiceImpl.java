package kr.mashup.branding.domain.sms;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class SmsServiceImpl {

    private final SmsSendServiceImpl smsSendService;
    private final SmsRequestServiceImpl smsRequestService;
    private final SmsRequestGroupServiceImpl smsRequestGroupService;

    public SmsRequestGroup sendSms(SmsRequestGroupVo smsRequestGroupVo, List<SmsRequestVo> smsRequestVoList) {
        SmsRequestGroup smsRequestGroup = smsRequestGroupService.create(smsRequestGroupVo);

        List<SmsRequest> smsRequests = smsRequestVoList.stream()
                .map(smsRequestService::createSmsRequest)
                .collect(Collectors.toList());

        smsRequests.forEach(request -> send(request));

        return smsRequestGroup;
    }

    public SmsRequestGroup retrySendSms(Long groupId) {
        SmsRequestGroup requestGroup = smsRequestGroupService.getRequestGroup(groupId);
        List<SmsRequest> failedRequests = smsRequestService.getFailedRequests(groupId);

        failedRequests.forEach(request -> send(request));

        return requestGroup;
    }

    public SmsRequestGroup getRequestGroup(Long id) {
        return smsRequestGroupService.getRequestGroup(id);
    }

    public List<SmsRequest> getRequests(Long groupId) {
        return smsRequestService.getRequests(groupId);
    }

    private void send(SmsRequest smsRequest) {
        try {
            // TODO: toast sms service 연동 ~ 1.29
            smsSendService.sendSmsUsingToast();
            smsRequestService.markAsSuccess(smsRequest);
        } catch (Exception e) {
            log.info("[SmsService] 문자 발송 실패. Id: " + smsRequest.getId() + ", userId: " + smsRequest.getUserId());
            smsRequestService.markAsFail(smsRequest);
        }
    }
}
