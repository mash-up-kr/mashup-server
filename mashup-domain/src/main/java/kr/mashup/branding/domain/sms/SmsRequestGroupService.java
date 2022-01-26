package kr.mashup.branding.domain.sms;

import java.util.List;

interface SmsRequestGroupService {
    SmsRequestGroup getRequestGroup(Long id);
    SmsRequestGroup createAndSave(SmsRequestGroupVo smsRequestGroupVo);
    List<SmsRequestGroup> getRequestGroups();
    void markAsComplete(SmsRequestGroup smsRequestGroup);
}
