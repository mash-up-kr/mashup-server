package kr.mashup.branding.service.danggn;

import java.util.Optional;

import org.springframework.stereotype.Service;

import kr.mashup.branding.domain.danggn.DanggnRankingReward;
import kr.mashup.branding.domain.danggn.DanggnRankingRound;
import kr.mashup.branding.repository.danggn.DanggnRankingRewardRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DanggnRankingRewardService {

	private final DanggnRankingRewardRepository danggnRankingRewardRepository;

	public DanggnRankingReward findByDanggnRankingRoundOrNull(Optional<DanggnRankingRound> danggnRankingRound) {
		return danggnRankingRound.flatMap(round -> danggnRankingRewardRepository.findByDanggnRankingRoundId(round.getId()))
			.orElse(null);
	}
}
