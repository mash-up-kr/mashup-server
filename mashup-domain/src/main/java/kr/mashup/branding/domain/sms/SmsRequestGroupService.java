package kr.mashup.branding.domain.sms;

interface SmsRequestGroupService {
    SmsRequestGroup getRequestGroup(Long id);
    SmsRequestGroup createAndSave(SmsRequestGroupVo smsRequestGroupVo);
    void markAsComplete(SmsRequestGroup smsRequestGroup);
}
