package kr.mashup.branding.ui.danggn.response;

import java.time.LocalDate;

import kr.mashup.branding.domain.danggn.DanggnRankingRound;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@AllArgsConstructor
@Getter
public class DanggnRankingRoundResponse {

	Integer number;

	LocalDate startedAt;

	LocalDate endedAt;

	DanggnRankingRewardResponse danggnRankingReward;

	public static DanggnRankingRoundResponse of(DanggnRankingRound danggnRankingRound, DanggnRankingRewardResponse danggnRankingReward) {
		return new DanggnRankingRoundResponse(
			danggnRankingRound.getNumber(),
			danggnRankingRound.getStartedAt().toLocalDate(),
			danggnRankingRound.getEndedAt().toLocalDate(),
			danggnRankingReward
		);
	}

	@Value(staticConstructor = "of")
	public static class DanggnRankingRewardResponse {

		String name;

		String comment;
	}
}
