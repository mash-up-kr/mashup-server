package kr.mashup.branding.domain.pushnoti.vo;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.member.Platform;
import kr.mashup.branding.domain.pushnoti.DataKeyType;
import kr.mashup.branding.domain.pushnoti.LinkType;

public class DanggnNotificationPlatformRecordUpdatedVo extends PushNotiSendVo {
    private static final String title = "%s팀이 당근 흔들기 누적 %s번을 돌파했어요";
    private static final String body = "어케했누;;; 전체 랭킹을 확인해보세요.";
    private static final Map<String, String> dataMap = Map.of(DataKeyType.LINK.getKey(), LinkType.DANGGN.toString());

    public DanggnNotificationPlatformRecordUpdatedVo(Long stage, Platform platform, List<Member> members) {
        super(members, String.format(title, platform.getName(), new DecimalFormat("###,###").format(stage)), body, dataMap);
    }
}
