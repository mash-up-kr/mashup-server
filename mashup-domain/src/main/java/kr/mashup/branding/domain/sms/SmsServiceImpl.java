package kr.mashup.branding.domain.sms;

import kr.mashup.branding.domain.sms.dto.ToastSmsResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class SmsServiceImpl implements SmsService {

    private final ToastSmsService toastSmsService;
    private final SmsRequestService smsRequestService;
    private final SmsRequestGroupService smsRequestGroupService;

    @Override
    public SmsRequestGroup sendSms(SmsRequestGroupVo smsRequestGroupVo, List<SmsRequestVo> smsRequestVoList) throws Exception {
        SmsRequestGroup smsRequestGroup = smsRequestGroupService.createAndSave(smsRequestGroupVo);

        List<SmsRequest> smsRequests = smsRequestVoList.stream()
                .map(smsRequestVo ->  smsRequestService.createSmsRequest(smsRequestGroup, smsRequestVo))
                .collect(Collectors.toList());
        smsRequestService.saveAll(smsRequests);

        ToastSmsResponse toastSmsResponse = toastSmsService.send(smsRequestGroup, smsRequestGroup.getSmsRequests());

        if (toastSmsResponse.getBody().getData().getStatusCode() == 2) {
            return smsRequestGroup;
        }

        smsRequestService.markRequests(toastSmsResponse, smsRequestGroup);
        smsRequestGroupService.markAsComplete(smsRequestGroup);

        return smsRequestGroup;
    }

    @Override
    public SmsRequestGroup retrySendSms(Long groupId) {
        SmsRequestGroup requestGroup = smsRequestGroupService.getRequestGroup(groupId);
        List<SmsRequest> failedRequests = smsRequestService.getFailedRequests(groupId);

        toastSmsService.send(requestGroup, failedRequests);

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
