package kr.mashup.branding.repository.emailnotification;

import kr.mashup.branding.domain.email.EmailNotification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface EmailNotificationRepositoryCustom {

    Page<EmailNotification> findBySearchWord(Optional<String> searchWord, Pageable pageable);
}
