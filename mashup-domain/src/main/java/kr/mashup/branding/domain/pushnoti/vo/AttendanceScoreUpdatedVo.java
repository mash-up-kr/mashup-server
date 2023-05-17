package kr.mashup.branding.domain.pushnoti.vo;

import java.util.List;
import java.util.Map;

import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.pushnoti.DataKeyType;
import kr.mashup.branding.domain.pushnoti.LinkType;

public class AttendanceScoreUpdatedVo extends PushNotiSendVo {
    private static final String title = "출석 점수 알림";
    private static final String body = "출석 점수가 업데이트됐어요. 확인해보숑";
    private static final Map<String, String> dataMap = Map.of(DataKeyType.LINK.getKey(), LinkType.MYPAGE.toString());

    public AttendanceScoreUpdatedVo(List<Member> members) {
        super(members, title, body, dataMap);
    }
}
