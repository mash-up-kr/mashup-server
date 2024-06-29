package kr.mashup.branding.domain.pushnoti.vo;

import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.pushnoti.DataKeyType;
import kr.mashup.branding.domain.pushnoti.LinkType;

import java.util.List;
import java.util.Map;

public class BirthdaySenderVo extends PushNotiSendVo {
    private static final String title = "생일인 매숑들을 축하해 주세요.";
    private static final String body = "오늘 생일인 매숑이들에게 축하 메세지를 남겨 보세요.";
    private static final Map<String, String> dataMap = Map.of(DataKeyType.LINK.getKey(), LinkType.BIRTHDAY.toString());

    public BirthdaySenderVo(List<Member> members) {
        super(members, title, body, dataMap);
    }
}
