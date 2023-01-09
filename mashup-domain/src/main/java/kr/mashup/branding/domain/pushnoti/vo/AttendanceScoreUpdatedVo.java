package kr.mashup.branding.domain.pushnoti.vo;

import kr.mashup.branding.domain.member.Member;

import java.util.List;

public class AttendanceScoreUpdatedVo extends PushNotiSendVo {
    private static final String title = "출석 점수 알림";
    private static final String body = "출석 점수가 업데이트됐어요. 확인해보숑";

    public AttendanceScoreUpdatedVo(List<Member> members) {
        super(members, title, body);
    }
}
