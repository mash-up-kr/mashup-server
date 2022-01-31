package kr.mashup.branding.domain.sms;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface SmsRequestRepository extends JpaRepository<SmsRequest, Long> {
    List<SmsRequest> findBySmsRequestGroup_smsRequestGroupIdAndStatus(Long smsRequestGroupId, SmsRequestStatus status);
}
