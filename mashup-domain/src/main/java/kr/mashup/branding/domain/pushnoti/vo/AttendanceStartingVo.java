package kr.mashup.branding.domain.pushnoti.vo;

import java.util.List;

public class AttendanceStartingVo extends PushNotiSendVo {
    private static final String title = "출석체크 알림";
    private static final String body = "출석체크가 곧 시작돼요";

    public AttendanceStartingVo(List<String> tokens) {
        super(tokens, title, body);
    }
}
