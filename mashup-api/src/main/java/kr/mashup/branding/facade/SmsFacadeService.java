package kr.mashup.branding.facade;

import kr.mashup.branding.domain.sms.SmsRequestGroupVo;
import kr.mashup.branding.domain.sms.SmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SmsFacadeService {

    private final SmsService smsService;

    public void sendSms() {
        smsService.sendSms(SmsRequestGroupVo.of("", ""), List.of());
    }

    public void getAllRequestGroup() {
        smsService.getRequestGroups();
    }

    public void getSmsRequests(Long groupId) {
        smsService.getRequests(groupId);
    }

    public void refreshRequestGroup() {

    }

    public void retrySendSms() {

    }

}
