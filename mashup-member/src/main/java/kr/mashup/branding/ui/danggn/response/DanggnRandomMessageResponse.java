package kr.mashup.branding.ui.danggn.response;

import kr.mashup.branding.domain.randommessage.RandomMessage;
import lombok.Getter;
import lombok.Value;

@Getter
@Value(staticConstructor = "of")
public class DanggnRandomMessageResponse {
    String todayMessage;

    public static DanggnRandomMessageResponse from(RandomMessage randomMessage) {
        return new DanggnRandomMessageResponse(randomMessage.getMessage());
    }
}
