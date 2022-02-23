package kr.mashup.branding.domain.notification.sms.whitelist;

public interface SmsWhitelistService {
    boolean contains(String phoneNumber);
}
