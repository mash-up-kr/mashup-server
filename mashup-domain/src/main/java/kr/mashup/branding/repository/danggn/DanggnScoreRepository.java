package kr.mashup.branding.repository.danggn;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.mashup.branding.domain.danggn.DanggnScore;
import kr.mashup.branding.domain.member.MemberGeneration;

public interface DanggnScoreRepository extends JpaRepository<DanggnScore, Long>, DanggnScoreRepositoryCustom {
    Optional<DanggnScore> findByMemberGeneration(MemberGeneration memberGeneration);

    void deleteByMemberGeneration(MemberGeneration memberGeneration);
}
