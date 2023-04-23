package kr.mashup.branding.ui.danggn.response;

import kr.mashup.branding.domain.danggn.DanggnTodayMessage;
import lombok.Getter;
import lombok.Value;

@Getter
@Value(staticConstructor = "of")
public class DanggnRandomMessageResponse {
    String todayMessage;

    public static DanggnRandomMessageResponse from(DanggnTodayMessage danggnTodayMessage) {
        return new DanggnRandomMessageResponse(danggnTodayMessage.getMessage());
    }
}
