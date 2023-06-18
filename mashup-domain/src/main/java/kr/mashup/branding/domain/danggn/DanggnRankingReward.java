package kr.mashup.branding.domain.danggn;

import javax.persistence.Entity;

import kr.mashup.branding.domain.BaseEntity;
import kr.mashup.branding.service.danggn.DanggnRankingRewardStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DanggnRankingReward extends BaseEntity {

	private String comment;

	private Long firstPlaceRecordMemberId;

	private Long danggnRankingRoundId;

	public static DanggnRankingReward from(Long firstPlaceRecordMemberGenerationId, Long danggnRankingRoundId) {
		return new DanggnRankingReward(firstPlaceRecordMemberGenerationId, danggnRankingRoundId);
	}

	private DanggnRankingReward(Long firstPlaceRecordMemberId, Long danggnRankingRoundId) {
		this.firstPlaceRecordMemberId = firstPlaceRecordMemberId;
		this.danggnRankingRoundId = danggnRankingRoundId;
	}

	public DanggnRankingRewardStatus getRankingRewardStatus() {
		return this.getComment() == null ? DanggnRankingRewardStatus.FIRST_PLACE_MEMBER_NOT_REGISTERED :
			DanggnRankingRewardStatus.FIRST_PLACE_MEMBER_REGISTERED;
	}
}
