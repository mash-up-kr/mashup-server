package kr.mashup.branding.service.danggn;

import kr.mashup.branding.domain.danggn.DanggnRankingReward;
import kr.mashup.branding.domain.danggn.DanggnRankingRound;
import kr.mashup.branding.domain.danggn.Exception.DanggnRankingRewardNotFoundException;
import kr.mashup.branding.repository.danggn.DanggnRankingRewardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DanggnRankingRewardService {

	private final DanggnRankingRewardRepository danggnRankingRewardRepository;

	public DanggnRankingReward findByDanggnRankingRoundOrNull(Optional<DanggnRankingRound> danggnRankingRound) {
		return danggnRankingRound.flatMap(round -> danggnRankingRewardRepository.findByDanggnRankingRoundId(round.getId()))
			.orElse(null);
	}

	public DanggnRankingReward findById(Long danggnRankingRoundId) {
		return danggnRankingRewardRepository.findById(danggnRankingRoundId).orElseThrow(DanggnRankingRewardNotFoundException::new);
	}

	public void writeComment(Long danggnRankingRoundId, String comment) {
		DanggnRankingReward danggnRankingReward = findById(danggnRankingRoundId);
		danggnRankingReward.setComment(comment);
	}

	public void save(DanggnRankingReward danggnRankingReward) {
		danggnRankingRewardRepository.save(danggnRankingReward);
	}

	public Boolean existsByDanggnRankingRoundId(Long danggnRankingRoundId) {
		return danggnRankingRewardRepository.existsByDanggnRankingRoundId(danggnRankingRoundId);
	}
}
