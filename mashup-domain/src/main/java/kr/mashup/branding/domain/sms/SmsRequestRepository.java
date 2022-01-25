package kr.mashup.branding.domain.sms;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface SmsRequestRepository extends JpaRepository<SmsRequest, Long> {
    List<SmsRequest> findAllBySmsRequestGroupIdAndStatus(SmsRequestGroup requestGroup, SmsRequestStatus status);
}
