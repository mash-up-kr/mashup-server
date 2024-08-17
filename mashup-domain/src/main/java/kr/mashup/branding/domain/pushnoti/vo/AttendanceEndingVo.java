package kr.mashup.branding.domain.pushnoti.vo;

import java.util.List;
import java.util.Map;

import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.pushnoti.DataKeyType;
import kr.mashup.branding.domain.pushnoti.LinkType;

public class AttendanceEndingVo extends PushNotiSendVo {
    private static final PushType pushType = PushType.ATTENDANCE;
    private static final String title = "출석체크 알림";
    private static final String body = "얼마 남지 않았어요.";
    private static final Map<String, String> dataMap = Map.of(DataKeyType.LINK.getKey(), LinkType.QR.toString());

    public AttendanceEndingVo(List<Member> members) {
        super(members, pushType, title, body, dataMap);
    }
}
