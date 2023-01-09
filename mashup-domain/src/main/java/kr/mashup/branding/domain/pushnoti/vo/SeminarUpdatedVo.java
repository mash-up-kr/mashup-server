package kr.mashup.branding.domain.pushnoti.vo;

import kr.mashup.branding.domain.member.Member;

import java.util.List;

public class SeminarUpdatedVo extends PushNotiSendVo {
    private static final String title = "세미나 알림";
    private static final String body = "세미나 정보가 업데이트 되었어요.";

    public SeminarUpdatedVo(List<Member> members) {
        super(members, title, body);
    }
}
