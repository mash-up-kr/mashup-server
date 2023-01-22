package kr.mashup.branding.domain.pushnoti.vo;

import kr.mashup.branding.domain.member.Member;

import java.util.List;

public class AttendanceEndingVo extends PushNotiSendVo {
    private static final String title = "출석체크 알림";
    private static final String body = "얼마 남지 않았어요.";

    public AttendanceEndingVo(List<Member> members) {
        super(members, title, body);
    }
}
