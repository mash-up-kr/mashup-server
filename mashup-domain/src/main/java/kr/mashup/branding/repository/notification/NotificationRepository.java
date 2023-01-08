package kr.mashup.branding.repository.notification;

import kr.mashup.branding.domain.notification.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long>, NotificationRepositoryCustom  {
    boolean existsByName(String name);

}
/**
 * Notification 연관관계
 * one to many : smsRequest
 * many to one : adminMember
 */