package kr.mashup.branding.domain.pushnoti.vo;

import kr.mashup.branding.domain.member.Member;

import java.util.List;

public class DanggnFirstRecordPlatformUpdatedVo extends PushNotiSendVo {
    private static final String title = "당근 흔들기 팀 랭킹 1위가 업데이트 됏어요";
    private static final String body = "과연 누가 1위를 탈환했을까요?";

    public DanggnFirstRecordPlatformUpdatedVo(List<Member> members) {
        super(members, title, body);
    }
}
