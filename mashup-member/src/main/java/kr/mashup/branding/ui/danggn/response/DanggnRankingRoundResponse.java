package kr.mashup.branding.ui.danggn.response;

import java.time.LocalDateTime;

import kr.mashup.branding.domain.danggn.DanggnRankingReward;
import kr.mashup.branding.domain.danggn.DanggnRankingRewardStatus;
import kr.mashup.branding.domain.danggn.DanggnRankingRound;
import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.util.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DanggnRankingRoundResponse {

	Integer number;

	LocalDateTime startDate;

	LocalDateTime endDate;

	Integer dateCount;

	DanggnRankingRewardResponse danggnRankingReward;

	Boolean isRunning;

	public static DanggnRankingRoundResponse of(DanggnRankingRound danggnRankingRound, DanggnRankingRewardResponse danggnRankingReward) {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime startedAt = danggnRankingRound.getStartedAt().toLocalDate().atStartOfDay();
		LocalDateTime endedAt = danggnRankingRound.getEndedAt().toLocalDate().plusDays(1L).atStartOfDay();
		return new DanggnRankingRoundResponse(
			danggnRankingRound.getNumber(),
			danggnRankingRound.getStartedAt(),
			danggnRankingRound.getEndedAt(),
			DateUtil.countDayFromNow(danggnRankingRound.getEndedAt(), now),
			danggnRankingReward,
			now.isAfter(startedAt) && now.isBefore(endedAt)
		);
	}

	@AllArgsConstructor
	@Getter
	public static class DanggnRankingRewardResponse {

		Long id;

		String name;

		String comment;

		DanggnRankingRewardStatus status;

		Boolean isFirstPlaceMember;

		public static DanggnRankingRewardResponse of(DanggnRankingReward danggnRankingReward, Member member, DanggnRankingRewardStatus danggnRankingRewardStatus, Boolean isFirstPlaceMember) {
			return new DanggnRankingRewardResponse(
				danggnRankingRewardStatus.hasFirstPlaceMember() ? danggnRankingReward.getId() : null,
				danggnRankingRewardStatus.hasFirstPlaceMember() ? member.getName() : null,
				danggnRankingRewardStatus.hasFirstPlaceMember() ? danggnRankingReward.getComment() : null,
				danggnRankingRewardStatus,
				isFirstPlaceMember
			);
		}
	}
}
