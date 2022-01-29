package kr.mashup.branding.facade;

import kr.mashup.branding.domain.sms.SmsRequestGroupVo;
import kr.mashup.branding.domain.sms.SmsService;
import kr.mashup.branding.ui.sms.dto.SmsRequestGroupResponse;
import kr.mashup.branding.ui.sms.dto.SmsRequestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SmsFacadeService {

    private final SmsService smsService;

    public void sendSms() {
        smsService.sendSms(SmsRequestGroupVo.of("", ""), List.of());
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
