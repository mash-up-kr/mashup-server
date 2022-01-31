package kr.mashup.branding.domain.sms;

import java.util.List;

public interface SmsRequestGroupService {
    SmsRequestGroup getRequestGroup(Long smsRequestGroupId);
    SmsRequestGroup createAndSave(SmsRequestGroupVo smsRequestGroupVo);
    List<SmsRequestGroup> getRequestGroups();
    void markAsComplete(SmsRequestGroup smsRequestGroup);
    void markAsFail(SmsRequestGroup smsRequestGroup);
}
