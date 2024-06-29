package kr.mashup.branding.domain.pushnoti.vo;

import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.pushnoti.DataKeyType;
import kr.mashup.branding.domain.pushnoti.LinkType;

import java.util.List;
import java.util.Map;

public class BirthdayRecipientVo extends PushNotiSendVo {
    private static final PushType pushType = PushType.BIRTHDAY;
    private static final String title = "오늘 생일이시군요";
    private static final String body = "매숑이들이 준 선물을 확인해보세요❤️";
    private static final Map<String, String> dataMap = Map.of(DataKeyType.LINK.getKey(), LinkType.BIRTHDAY.toString());

    public BirthdayRecipientVo(List<Member> members) {
        super(members, pushType, title, body, dataMap);
    }
}
