package kr.mashup.branding.ui.pushnoti.request;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class BroadCastPushNotiRequest {
    @NotNull
    private String title;

    @NotNull
    private String body;
}
