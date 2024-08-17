package kr.mashup.branding.domain.pushnoti.vo;

import java.util.List;
import java.util.Map;

import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.member.Platform;
import kr.mashup.branding.domain.pushnoti.DataKeyType;
import kr.mashup.branding.domain.pushnoti.LinkType;

public class DanggnFirstRecordPlatformUpdatedVo extends PushNotiSendVo {
    private static final PushType pushType = PushType.DANGGN;
    private static final String title = "당근 흔들기 팀 랭킹 1위 업데이트";
    private static final String body = "%s팀이 1위를 차지했어요!";
    private static final Map<String, String> dataMap = Map.of(DataKeyType.LINK.getKey(), LinkType.DANGGN.toString());

    public DanggnFirstRecordPlatformUpdatedVo(Platform platform, List<Member> members) {
        super(members, pushType, title, String.format(body, platform.getName()), dataMap);
    }
}
