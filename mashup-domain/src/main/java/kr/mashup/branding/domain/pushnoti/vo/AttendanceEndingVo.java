package kr.mashup.branding.domain.pushnoti.vo;

import java.util.List;

public class AttendanceEndingVo extends PushNotiSendVo {
    private static final String title = "출석체크 알림";
    private static final String body = "얼마 남지 않았어요.";

    public AttendanceEndingVo(List<String> tokens) {
        super(tokens, title, body);
    }
}
