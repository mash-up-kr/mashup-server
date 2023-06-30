package kr.mashup.branding.repository.danggn;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.mashup.branding.domain.danggn.DanggnRankingReward;

public interface DanggnRankingRewardRepository extends JpaRepository<DanggnRankingReward, Long> {

	Optional<DanggnRankingReward> findByDanggnRankingRoundId(Long danggnRankingRoundId);

	Boolean existsByDanggnRankingRoundId(Long danggnRankingRoundId);
}
