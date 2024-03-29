package kr.mashup.branding.repository.danggn;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.mashup.branding.domain.danggn.DanggnScore;
import kr.mashup.branding.domain.member.MemberGeneration;

public interface DanggnScoreRepository extends JpaRepository<DanggnScore, Long>, DanggnScoreRepositoryCustom {
    Optional<DanggnScore> findByMemberGenerationAndDanggnRankingRoundId(MemberGeneration memberGeneration, Long danggnRankingRoundId);

    void deleteByMemberGeneration(MemberGeneration memberGeneration);
}
