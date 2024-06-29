package kr.mashup.branding.ui.danggn;

import kr.mashup.branding.domain.randommessage.RandomMessageType;
import lombok.Getter;

@Getter
public class TodayMessageRequest {
    private String message;
    private RandomMessageType randomMessageType = RandomMessageType.DANGGN;
}
