package kr.mashup.branding.domain.pushnoti.vo;

import java.util.List;
import java.util.Map;

import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.pushnoti.DataKeyType;
import kr.mashup.branding.domain.pushnoti.LinkType;

public class SeminarBadgeReminderVo extends PushNotiSendVo {
    private static final PushType pushType = PushType.SEMINAR;
    private static final String title = "📌 세미나 준비 알림";
    private static final Map<String, String> dataMap = Map.of(DataKeyType.LINK.getKey(), LinkType.MAIN.toString());

    public SeminarBadgeReminderVo(List<Member> leaders, String scheduleName) {
        super(leaders, pushType, title,
              "오늘 " + scheduleName + " 오프라인 세미나가 있어요. 명찰을 챙겨주세요!", dataMap);
    }
}
