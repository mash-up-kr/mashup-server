package kr.mashup.branding.domain.rnb;

public enum RnbPolicy {

    ALL("전체 공개"),
    IN_CURRENT_GENERATION_ACTIVE("현재 기수 중이고, 최신 기수 활동 중인 사람"),
    ELSE("조건에 해당하지 않는 여집합"),
    HIDE("숨기기");

    private String desc;

    RnbPolicy(String desc) {
        this.desc = desc;
    }
}
