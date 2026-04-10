package kr.mashup.branding.domain.pushnoti.vo;

import java.util.List;
import java.util.Map;

import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.pushnoti.DataKeyType;
import kr.mashup.branding.domain.pushnoti.LinkType;

public class AttendanceAbsentForLeaderVo extends PushNotiSendVo {
    private static final PushType pushType = PushType.ATTENDANCE;
    private static final String title = "⚠️ 출석 현황 알림";
    private static final Map<String, String> dataMap = Map.of(DataKeyType.LINK.getKey(), LinkType.MAIN.toString());

    public AttendanceAbsentForLeaderVo(List<Member> leaders, String platformName, String absentNames) {
        super(leaders, pushType, title,
              platformName + "에서 출석하지 못한 멤버가 있어요: " + absentNames, dataMap);
    }
}
