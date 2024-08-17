package kr.mashup.branding.ui.pushnoti.request;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
public class SendMePushNotiRequest {
    @NotNull
    private String title;

    @NotNull
    private String body;

    private List<Long> memberIds;

    private String keyType = "LINK";

    private String linkType = "MAIN";
}
