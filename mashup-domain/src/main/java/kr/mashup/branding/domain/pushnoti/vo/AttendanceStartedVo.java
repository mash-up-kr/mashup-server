package kr.mashup.branding.domain.pushnoti.vo;

import java.util.List;

public class AttendanceStartedVo extends PushNotiSendVo {
    private static final String title = "출석체크 알림";
    private static final String body = "출석체크가 시작되었습니다.";

    public AttendanceStartedVo(List<String> tokens) {
        super(tokens, title, body);
    }
}
