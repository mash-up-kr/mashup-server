package kr.mashup.branding.domain.sms;

import kr.mashup.branding.domain.sms.dto.ToastSmsRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class SmsServiceImpl implements SmsService{

    private final ToastSmsService toastSmsService;
    private final SmsRequestService smsRequestService;
    private final SmsRequestGroupService smsRequestGroupService;

    @Override
    public SmsRequestGroup sendSms(SmsRequestGroupVo smsRequestGroupVo, List<SmsRequestVo> smsRequestVoList) {
        SmsRequestGroup smsRequestGroup = smsRequestGroupService.create(smsRequestGroupVo);

        List<SmsRequest> smsRequests = smsRequestVoList.stream()
                .map(smsRequestService::createSmsRequest)
                .collect(Collectors.toList());

        smsRequests.forEach(request -> send(request));

        return smsRequestGroup;
    }

    @Override
    public SmsRequestGroup retrySendSms(Long groupId) {
        SmsRequestGroup requestGroup = smsRequestGroupService.getRequestGroup(groupId);
        List<SmsRequest> failedRequests = smsRequestService.getFailedRequests(groupId);

        failedRequests.forEach(request -> send(request));

        return requestGroup;
    }

    @Override
    public SmsRequestGroup getRequestGroup(Long id) {
        return smsRequestGroupService.getRequestGroup(id);
    }

    @Override
    public List<SmsRequest> getRequests(Long groupId) {
        return smsRequestService.getRequests(groupId);
    }

    private void send(SmsRequest smsRequest) {
        try {
            toastSmsService.send(new ToastSmsRequest());
            smsRequestService.markAsSuccess(smsRequest);
        } catch (Exception e) {
            log.info("[SmsService] 문자 발송 실패. Id: " + smsRequest.getId() + ", userId: " + smsRequest.getUserId());
            smsRequestService.markAsFail(smsRequest);
        }
    }
}
