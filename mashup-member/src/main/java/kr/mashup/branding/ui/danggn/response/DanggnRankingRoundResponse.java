package kr.mashup.branding.ui.danggn.response;

import kr.mashup.branding.domain.danggn.DanggnRankingReward;
import kr.mashup.branding.domain.danggn.DanggnRankingRewardStatus;
import kr.mashup.branding.domain.danggn.DanggnRankingRound;
import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.util.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class DanggnRankingRoundResponse {

	Integer number;

	LocalDateTime startDate;

	LocalDateTime endDate;

	Integer dateCount;

	DanggnRankingRewardResponse danggnRankingReward;

	public static DanggnRankingRoundResponse of(DanggnRankingRound danggnRankingRound, DanggnRankingRewardResponse danggnRankingReward) {
		return new DanggnRankingRoundResponse(
			danggnRankingRound.getNumber(),
			danggnRankingRound.getStartedAt(),
			danggnRankingRound.getEndedAt(),
			DateUtil.countDayFromNow(danggnRankingRound.getEndedAt(), LocalDateTime.now()),
			danggnRankingReward
		);
	}

	@AllArgsConstructor
	@Getter
	public static class DanggnRankingRewardResponse {

		Long id;

		String name;

		String comment;

		DanggnRankingRewardStatus status;

		public static DanggnRankingRewardResponse of(DanggnRankingReward danggnRankingReward, Member member, DanggnRankingRewardStatus danggnRankingRewardStatus) {
			return new DanggnRankingRewardResponse(
				danggnRankingRewardStatus.hasFirstPlaceMember() ? danggnRankingReward.getId() : null,
				danggnRankingRewardStatus.hasFirstPlaceMember() ? member.getName() : null,
				danggnRankingRewardStatus.hasFirstPlaceMember() ? danggnRankingReward.getComment() : null,
				danggnRankingRewardStatus
			);
		}
	}
}
