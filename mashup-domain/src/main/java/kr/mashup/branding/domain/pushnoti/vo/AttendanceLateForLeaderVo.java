package kr.mashup.branding.domain.pushnoti.vo;

import java.util.List;
import java.util.Map;

import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.pushnoti.DataKeyType;
import kr.mashup.branding.domain.pushnoti.LinkType;

public class AttendanceLateForLeaderVo extends PushNotiSendVo {
    private static final PushType pushType = PushType.ATTENDANCE;
    private static final String title = "⏰ 출석 현황 알림";
    private static final Map<String, String> dataMap = Map.of(DataKeyType.LINK.getKey(), LinkType.MAIN.toString());

    public AttendanceLateForLeaderVo(List<Member> leaders, String platformName, String lateNames) {
        super(leaders, pushType, title,
              platformName + "에서 아직 출석하지 않은 멤버가 있어요: " + lateNames, dataMap);
    }
}
