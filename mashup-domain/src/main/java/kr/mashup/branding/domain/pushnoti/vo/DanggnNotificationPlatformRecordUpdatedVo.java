package kr.mashup.branding.domain.pushnoti.vo;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.member.Platform;
import kr.mashup.branding.domain.pushnoti.DataKeyType;
import kr.mashup.branding.domain.pushnoti.LinkType;

public class DanggnNotificationPlatformRecordUpdatedVo extends PushNotiSendVo {
    private static final PushType pushType = PushType.DANGGN;
    private static final String title = "팀의 당근 흔들기 소식";
    private static final String body = "%s팀이 당근 흔들기 누적 %s번을 돌파했어요";
    private static final Map<String, String> dataMap = Map.of(DataKeyType.LINK.getKey(), LinkType.DANGGN.toString());

    public DanggnNotificationPlatformRecordUpdatedVo(Long stage, Platform platform, List<Member> members) {
        super(members, pushType, title, String.format(body, platform.getName(), new DecimalFormat("###,###").format(stage)), dataMap);
    }
}
