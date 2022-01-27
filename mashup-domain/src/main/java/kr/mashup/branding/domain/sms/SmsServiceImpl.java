package kr.mashup.branding.domain.sms;

import kr.mashup.branding.domain.sms.dto.ToastSmsResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class SmsServiceImpl implements SmsService {

    private final ToastSmsService toastSmsService;
    private final SmsRequestService smsRequestService;
    private final SmsRequestGroupService smsRequestGroupService;

    @Override
    public SmsRequestGroup sendSms(SmsRequestGroupVo smsRequestGroupVo, List<SmsRequestVo> smsRequestVoList){
        SmsRequestGroup smsRequestGroup = smsRequestGroupService.createAndSave(smsRequestGroupVo);
        smsRequestService.createAndSaveAll(smsRequestGroup, smsRequestVoList);

        ToastSmsResponse toastSmsResponse = toastSmsService.send(smsRequestGroup, smsRequestGroup.getSmsRequests());

        if (toastSmsResponse.getBody().getData().getStatusCode() == 2) {
            return smsRequestGroup;
        }

        try {
            smsRequestService.markRequests(toastSmsResponse, smsRequestGroup);
        } catch (Exception e) {
            log.error("문자 메세지 발송 실패");
        }

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
    public List<SmsRequestGroup> getRequestGroups() {
        return smsRequestGroupService.getRequestGroups();
    }

    @Override
    public List<SmsRequest> getRequests(Long groupId) {
        return smsRequestService.getRequests(groupId);
    }
}
