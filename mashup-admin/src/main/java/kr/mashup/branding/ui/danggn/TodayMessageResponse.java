package kr.mashup.branding.ui.danggn;

import kr.mashup.branding.domain.randommessage.RandomMessage;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TodayMessageResponse {
    private Long id;
    private String message;

    public static TodayMessageResponse from(RandomMessage randomMessage) {
        return TodayMessageResponse.builder()
                .id(randomMessage.getId())
                .message(randomMessage.getMessage())
                .build();
    }
}
