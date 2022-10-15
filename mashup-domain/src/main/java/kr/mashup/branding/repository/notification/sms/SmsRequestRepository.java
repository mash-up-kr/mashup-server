package kr.mashup.branding.repository.notification.sms;

import kr.mashup.branding.domain.notification.sms.SmsRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SmsRequestRepository extends JpaRepository<SmsRequest, Long>, SmsRequestRepositoryCustom {


}
/**
 * Sms Request 연관관계
 * many to one : notification, applicant
 */