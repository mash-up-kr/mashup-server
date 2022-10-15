package kr.mashup.branding.repository.notification.sms;

import java.util.Optional;

import kr.mashup.branding.domain.notification.sms.whitelist.SmsWhitelist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SmsWhitelistRepository extends JpaRepository<SmsWhitelist, Long> {
    boolean existsByPhoneNumber(String phoneNumber);

    Optional<SmsWhitelist> findByPhoneNumber(String phoneNumber);
}
/**
 * 연관관계 없음
 */
