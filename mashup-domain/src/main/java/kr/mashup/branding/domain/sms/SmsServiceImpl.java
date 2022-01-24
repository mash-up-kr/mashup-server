package kr.mashup.branding.domain.sms;

import kr.mashup.branding.domain.sms.dto.ToastSmsResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class SmsServiceImpl implements SmsService {

    private final ToastSmsService toastSmsService;
    private final SmsRequestService smsRequestService;
    private final SmsRequestGroupService smsRequestGroupService;

    @Override
    public SmsRequestGroup sendSms(SmsRequestGroupVo smsRequestGroupVo, List<SmsRequestVo> smsRequestVoList) {
        SmsRequestGroup smsRequestGroup = smsRequestGroupService.createAndSave(smsRequestGroupVo);

        List<SmsRequest> smsRequests = smsRequestVoList.stream()
                .map(smsRequestService::createSmsRequest)
                .collect(Collectors.toList());
        smsRequestService.saveAll(smsRequests);

        List<SmsRequest> savedRequests = smsRequestService.getRequests(smsRequestGroup.getId());
        ToastSmsResponse toastSmsResponse = toastSmsService.send(savedRequests);

        smsRequestService.markRequests(toastSmsResponse, savedRequests);

        return smsRequestGroup;
    }

    @Override
    public SmsRequestGroup retrySendSms(Long groupId) {
        SmsRequestGroup requestGroup = smsRequestGroupService.getRequestGroup(groupId);
        List<SmsRequest> failedRequests = smsRequestService.getFailedRequests(groupId);

        toastSmsService.send(failedRequests);

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
}
