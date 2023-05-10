package kr.mashup.branding.repository.danggn;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.mashup.branding.domain.danggn.DanggnNotificationMemberRecord;
import kr.mashup.branding.domain.member.MemberGeneration;

public interface DanggnNotificationMemberRecordRepository extends JpaRepository<DanggnNotificationMemberRecord, Long> {

	Optional<DanggnNotificationMemberRecord> findByMemberGeneration(MemberGeneration memberGeneration);
}
