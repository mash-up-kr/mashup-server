package kr.mashup.branding.domain.pushnoti.vo;

import java.util.List;

public class SeminarAttendanceAppliedVo extends PushNotiSendVo {
    private static final String title = "세미나 출석 알림";
    private static final String body = "세미나 출석 결과가 반영되었어요. 마이페이지에서 확인해보세요!";

    public SeminarAttendanceAppliedVo(List<String> tokens) {
        super(tokens, title, body);
    }
}
