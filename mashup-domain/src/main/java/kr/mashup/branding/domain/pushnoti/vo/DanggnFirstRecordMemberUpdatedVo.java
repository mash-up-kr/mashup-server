package kr.mashup.branding.domain.pushnoti.vo;

import java.util.List;
import java.util.Map;

import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.pushnoti.DataKeyType;
import kr.mashup.branding.domain.pushnoti.LinkType;

public class DanggnFirstRecordMemberUpdatedVo extends PushNotiSendVo {
    private static final String title = "당근 흔들기 개인 랭킹 1위가 업데이트 됐어요";
    private static final String body = "과연 누가 1위를 탈환했을까요?";
    private static final Map<String, String> dataMap = Map.of(DataKeyType.LINK.getKey(), LinkType.DANGGN.toString());

    public DanggnFirstRecordMemberUpdatedVo(List<Member> members) {
        super(members, title, body, dataMap);
    }
}
