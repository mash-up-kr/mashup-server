package kr.mashup.branding.domain.danggn;

public enum DanggnRankingRewardStatus {

	FIRST_PLACE_MEMBER_NOT_EXISTED,		// 1등한 사람이 없는 경우(1회차)
	FIRST_PLACE_MEMBER_NOT_REGISTERED,	// 회차는 진행되었지만, 1등한 사람이 리워드를 등록하지 않은 경우
	FIRST_PLACE_MEMBER_REGISTERED		// 회차가 진행되었고 1등한 사람이 리워드를 등록한 경우
	;

	public Boolean hasFirstPlaceMember() {
		return this != FIRST_PLACE_MEMBER_NOT_EXISTED;
	}
}
