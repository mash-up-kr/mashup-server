package kr.mashup.branding.domain.sms;

import java.util.List;
import java.util.stream.Collectors;

public interface SmsService {
    SmsRequestGroup sendSms(SmsRequestGroupVo smsRequestGroupVo, List<SmsRequestVo> smsRequestVoList) throws Exception;
    SmsRequestGroup retrySendSms(Long groupId);
    SmsRequestGroup getRequestGroup(Long id);
    List<SmsRequest> getRequests(Long groupId);
}
