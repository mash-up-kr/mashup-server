package kr.mashup.branding.domain.pushnoti.vo;

import lombok.Getter;

import java.util.List;

@Getter
public class PushNotiSendVo {
    private final List<String> tokens;
    private final String title;
    private final String body;

    public PushNotiSendVo(List<String> tokens, String title, String body) {
        this.tokens = tokens;
        this.title = title;
        this.body = body;
    }
}
