package kr.mashup.branding.domain.pushnoti.vo;

import kr.mashup.branding.domain.member.Member;

import java.util.List;

public class AttendanceStartingVo extends PushNotiSendVo {
    private static final String title = "출석체크 알림";
    private static final String body = "출석체크가 곧 시작돼요";

    public AttendanceStartingVo(List<Member> members) {
        super(members, title, body);
    }
}
