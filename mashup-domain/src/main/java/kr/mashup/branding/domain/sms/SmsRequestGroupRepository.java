package kr.mashup.branding.domain.sms;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface SmsRequestGroupRepository extends JpaRepository<SmsRequestGroup, Long> {
}
