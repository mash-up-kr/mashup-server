package kr.mashup.branding.repository.danggn;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.mashup.branding.domain.danggn.DanggnShakeLog;
import kr.mashup.branding.domain.member.MemberGeneration;

public interface DanggnShakeLogRepository extends JpaRepository<DanggnShakeLog, Long> {

	void deleteByMemberGeneration(MemberGeneration memberGeneration);
}
