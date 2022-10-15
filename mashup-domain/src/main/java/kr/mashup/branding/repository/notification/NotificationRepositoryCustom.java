package kr.mashup.branding.repository.notification;

import kr.mashup.branding.domain.notification.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotificationRepositoryCustom {
    Page<Notification> findWithSearchWord(String searchWord, Pageable pageable);
}
