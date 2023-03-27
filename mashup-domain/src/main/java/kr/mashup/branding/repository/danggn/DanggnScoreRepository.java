package kr.mashup.branding.repository.danggn;

import kr.mashup.branding.domain.danggn.DanggnScore;
import kr.mashup.branding.domain.member.MemberGeneration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DanggnScoreRepository extends JpaRepository<DanggnScore, Long> {
    Optional<DanggnScore> findByMemberGeneration(MemberGeneration memberGeneration);
}