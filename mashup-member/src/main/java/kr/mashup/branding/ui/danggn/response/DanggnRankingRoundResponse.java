package kr.mashup.branding.ui.danggn.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import kr.mashup.branding.domain.danggn.DanggnRankingRound;
import kr.mashup.branding.util.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@AllArgsConstructor
@Getter
public class DanggnRankingRoundResponse {

	Integer number;

	LocalDate startedAt;

	LocalDate endedAt;

	Integer dateCount;

	DanggnRankingRewardResponse danggnRankingReward;

	public static DanggnRankingRoundResponse of(DanggnRankingRound danggnRankingRound, DanggnRankingRewardResponse danggnRankingReward) {
		return new DanggnRankingRoundResponse(
			danggnRankingRound.getNumber(),
			danggnRankingRound.getStartedAt().toLocalDate(),
			danggnRankingRound.getEndedAt().toLocalDate(),
			DateUtil.countDayFromNow(danggnRankingRound.getEndedAt(), LocalDateTime.now()),
			danggnRankingReward
		);
	}

	@Value(staticConstructor = "of")
	public static class DanggnRankingRewardResponse {

		String name;

		String comment;
	}
}
