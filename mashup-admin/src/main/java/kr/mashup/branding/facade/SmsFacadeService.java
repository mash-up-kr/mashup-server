package kr.mashup.branding.facade;

import kr.mashup.branding.domain.sms.*;
import kr.mashup.branding.domain.sms.dto.ToastSmsResponse;
import kr.mashup.branding.domain.sms.exception.SmsSendFailException;
import kr.mashup.branding.domain.sms.exception.ToastException;
import kr.mashup.branding.ui.sms.dto.SmsRequestGroupResponse;
import kr.mashup.branding.ui.sms.dto.SmsRequestResponse;
import kr.mashup.branding.ui.sms.dto.SmsSendRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SmsFacadeService {

    private final ToastSmsService toastSmsService;
    private final SmsRequestService smsRequestService;
    private final SmsRequestGroupService smsRequestGroupService;

    public void sendSms(SmsSendRequest request) {
        SmsRequestGroup requestGroup = smsRequestGroupService.createAndSave(SmsRequestGroupVo.of("", ""));
        List<SmsRequestVo> smsRequestVoList = request.getUserIds().stream()
                .map(userId -> SmsRequestVo.of(userId, userId.toString(), "", ""))
                .collect(Collectors.toList());
        List<SmsRequest> smsRequests = smsRequestService.createAndSaveAll(requestGroup, smsRequestVoList);

        ToastSmsResponse toastSmsResponse;
        try {
            toastSmsResponse = toastSmsService.send(requestGroup, smsRequests);
        } catch (Exception e) {
            smsRequestGroupService.markAsFail(requestGroup);
            throw new ToastException();
        }

        if (toastSmsResponse.isInProgress()) {
            return;
        }
        if (!toastSmsResponse.isSuccess()) {
            smsRequestGroupService.markAsFail(requestGroup);
            throw new SmsSendFailException();
        }
        smsRequestService.markRequestsWithToastResponse(toastSmsResponse, requestGroup);
        smsRequestGroupService.markAsComplete(requestGroup);
    }

    public List<SmsRequestGroupResponse> getAllRequestGroup() {
        return smsRequestGroupService.getRequestGroups().stream()
                .map(SmsRequestGroupResponse::of)
                .collect(Collectors.toList());
    }

    public List<SmsRequestResponse> getSmsRequests(Long groupId) {
        return smsRequestService.getRequests(groupId).stream()
                .map(SmsRequestResponse::of)
                .collect(Collectors.toList());
    }

    public void refreshRequestGroup() {

    }

    public void retrySendSms() {

    }

}
