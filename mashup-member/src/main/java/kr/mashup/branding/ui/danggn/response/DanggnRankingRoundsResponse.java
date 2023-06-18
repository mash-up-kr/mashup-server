package kr.mashup.branding.ui.danggn.response;

import java.time.LocalDate;
import java.util.List;

import kr.mashup.branding.domain.danggn.DanggnRankingRound;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@Value(staticConstructor = "from")
public class DanggnRankingRoundsResponse {

	List<DanggnRankingRoundSimpleResponse> DanggnRankingRounds;

	@AllArgsConstructor
	@Getter
	public static class DanggnRankingRoundSimpleResponse {

		Long id;

		Integer number;

		LocalDate startDate;

		LocalDate endDate;

		public static DanggnRankingRoundSimpleResponse from(DanggnRankingRound danggnRankingRound) {
			return new DanggnRankingRoundSimpleResponse(
				danggnRankingRound.getId(),
				danggnRankingRound.getNumber(),
				danggnRankingRound.getStartedAt().toLocalDate(),
				danggnRankingRound.getEndedAt().toLocalDate()
			);
		}
	}
}
