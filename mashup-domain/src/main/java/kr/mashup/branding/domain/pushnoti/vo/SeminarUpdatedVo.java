package kr.mashup.branding.domain.pushnoti.vo;

import java.util.List;
import java.util.Map;

import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.pushnoti.DataKeyType;
import kr.mashup.branding.domain.pushnoti.LinkType;

public class SeminarUpdatedVo extends PushNotiSendVo {
    private static final String title = "세미나 알림";
    private static final String body = "세미나 정보가 업데이트 되었어요.";
    private static final Map<String, String> dataMap = Map.of(DataKeyType.LINK.getKey(), LinkType.MAIN.toString());

    public SeminarUpdatedVo(List<Member> members) {
        super(members, title, body, dataMap);
    }
}
