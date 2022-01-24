package kr.mashup.branding.domain.sms;

interface SmsRequestGroupService {
    SmsRequestGroup getRequestGroup(Long id);
    SmsRequestGroup create(SmsRequestGroupVo smsRequestGroupVo);
}
