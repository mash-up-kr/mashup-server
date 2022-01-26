package kr.mashup.branding.domain.sms;

import java.util.List;

public interface SmsService {
    SmsRequestGroup sendSms(SmsRequestGroupVo smsRequestGroupVo, List<SmsRequestVo> smsRequestVoList);
    SmsRequestGroup retrySendSms(Long groupId);
    SmsRequestGroup getRequestGroup(Long id);
    List<SmsRequestGroup> getRequestGroups();
    List<SmsRequest> getRequests(Long groupId);
}
