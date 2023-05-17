package kr.mashup.branding.ui.danggn;

import kr.mashup.branding.domain.danggn.DanggnTodayMessage;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TodayMessageResponse {
    private Long id;
    private String message;

    public static TodayMessageResponse from(DanggnTodayMessage danggnTodayMessage) {
        return TodayMessageResponse.builder()
                .id(danggnTodayMessage.getId())
                .message(danggnTodayMessage.getMessage())
                .build();
    }
}
