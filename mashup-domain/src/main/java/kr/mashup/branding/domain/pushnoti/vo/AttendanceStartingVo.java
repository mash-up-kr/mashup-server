package kr.mashup.branding.domain.pushnoti.vo;

import java.util.List;
import java.util.Map;

import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.pushnoti.DataKeyType;
import kr.mashup.branding.domain.pushnoti.LinkType;

public class AttendanceStartingVo extends PushNotiSendVo {
    private static final String title = "출석체크 알림";
    private static final String body = "출석체크가 곧 시작돼요";
    private static final Map<String, String> dataMap = Map.of(DataKeyType.LINK.getKey(), LinkType.QR.toString());

    public AttendanceStartingVo(List<Member> members) {
        super(members, title, body, dataMap);
    }
}
