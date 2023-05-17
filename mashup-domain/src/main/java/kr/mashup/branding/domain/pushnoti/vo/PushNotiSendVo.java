package kr.mashup.branding.domain.pushnoti.vo;

import java.util.List;
import java.util.Map;

import kr.mashup.branding.domain.member.Member;
import lombok.Getter;

@Getter
public class PushNotiSendVo {
    private final List<Member> members;
    private final String title;
    private final String body;
    private final Map<String, String> dataMap;

    public PushNotiSendVo(List<Member> members, String title, String body, Map<String, String> dataMap) {
        this.members = members;
        this.title = title;
        this.body = body;
        this.dataMap = dataMap;
    }
}
