package kr.mashup.branding.domain.pushnoti.vo;

import java.util.List;
import java.util.Map;

import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.pushnoti.DataKeyType;
import kr.mashup.branding.domain.pushnoti.LinkType;

public class SeminarAttendanceAppliedVo extends PushNotiSendVo {
    private static final String title = "세미나 출석 알림";
    private static final String body = "세미나 출석 결과가 반영되었어요. 마이페이지에서 확인해보세요!";
    private static final Map<String, String> dataMap = Map.of(DataKeyType.LINK.getKey(), LinkType.MYPAGE.toString());

    public SeminarAttendanceAppliedVo(List<Member> members) {
        super(members, title, body, dataMap);
    }
}
