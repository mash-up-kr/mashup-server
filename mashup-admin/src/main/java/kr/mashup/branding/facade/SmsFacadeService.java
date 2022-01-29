package kr.mashup.branding.facade;

import kr.mashup.branding.domain.sms.SmsRequestGroupVo;
import kr.mashup.branding.domain.sms.SmsRequestVo;
import kr.mashup.branding.domain.sms.SmsService;
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

    private final SmsService smsService;

    public void sendSms(SmsSendRequest request) {
        List<SmsRequestVo> smsRequestVoList = request.getUserIds().stream()
                .map(userId -> SmsRequestVo.of(userId, "이정원", "01000000000"))
                .collect(Collectors.toList());
        smsService.sendSms(SmsRequestGroupVo.of("", ""), smsRequestVoList);
    }

    public List<SmsRequestGroupResponse> getAllRequestGroup() {
        return smsService.getRequestGroups().stream()
                .map(SmsRequestGroupResponse::of)
                .collect(Collectors.toList());
    }

    public List<SmsRequestResponse> getSmsRequests(Long groupId) {
        return smsService.getRequests(groupId).stream()
                .map(SmsRequestResponse::of)
                .collect(Collectors.toList());
    }

    public void refreshRequestGroup() {

    }

    public void retrySendSms() {

    }

}
