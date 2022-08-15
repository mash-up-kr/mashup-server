package kr.mashup.branding.service.notification.sms;

import java.util.List;

public interface SmsWhitelistService {
    boolean contains(String phoneNumber);

    String add(String phoneNumber);

    void remove(String phoneNumber);

    List<String> getAll();
}
