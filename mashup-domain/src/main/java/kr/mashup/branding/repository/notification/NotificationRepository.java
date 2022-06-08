package kr.mashup.branding.repository.notification;

import kr.mashup.branding.domain.notification.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    boolean existsByName(String name);

    // TODO: 발송자 조건 추가
    Page<Notification> findByNameContainsOrSenderValueContains(String nameContains, String senderValueContains,
        Pageable pageable);
}
