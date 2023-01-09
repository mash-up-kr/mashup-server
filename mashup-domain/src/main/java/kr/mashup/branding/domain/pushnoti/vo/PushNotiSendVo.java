package kr.mashup.branding.domain.pushnoti.vo;

import kr.mashup.branding.domain.member.Member;
import lombok.Getter;

import java.util.List;

@Getter
public class PushNotiSendVo {
    private final List<Member> members;
    private final String title;
    private final String body;

    public PushNotiSendVo(List<Member> members, String title, String body) {
        this.members = members;
        this.title = title;
        this.body = body;
    }
}
