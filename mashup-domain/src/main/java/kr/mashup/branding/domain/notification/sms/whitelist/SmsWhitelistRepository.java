package kr.mashup.branding.domain.notification.sms.whitelist;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SmsWhitelistRepository extends JpaRepository<SmsWhitelist, Long> {
    boolean existsByPhoneNumber(String phoneNumber);
}
