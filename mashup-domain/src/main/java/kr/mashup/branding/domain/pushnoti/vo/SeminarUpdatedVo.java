package kr.mashup.branding.domain.pushnoti.vo;

import java.util.List;
import java.util.Map;

import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.pushnoti.DataKeyType;
import kr.mashup.branding.domain.pushnoti.LinkType;
import kr.mashup.branding.domain.schedule.ScheduleType;

public class SeminarUpdatedVo extends PushNotiSendVo {
    public static final PushType pushType = PushType.SEMINAR;
    private static final String title = "세미나 알림";
    private static final String body = "세미나 정보가 업데이트 되었어요.";
    private static final Map<String, String> dataMap = Map.of(DataKeyType.LINK.getKey(), LinkType.MAIN.toString());

    public SeminarUpdatedVo(List<Member> members, ScheduleType scheduleType) {
        super(members, pushType, title, generatePushBody(body, scheduleType), dataMap);
    }

    private static String generatePushBody(String body, ScheduleType scheduleType) {
        switch (scheduleType) {
            case ALL:
                return "전체 " + body;
            case IOS:
                return "iOS팀 " + body;
            case WEB:
                return "웹팀 " + body;
            case NODE:
                return "노드팀 " + body;
            case DESIGN:
                return "디자인팀 " + body;
            case SPRING:
                return "스프링팀 " + body;
            case ANDROID:
                return "안드로이드팀 " + body;
        }
        throw new IllegalStateException();
    }
}
