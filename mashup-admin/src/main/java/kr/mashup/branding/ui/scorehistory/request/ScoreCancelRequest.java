package kr.mashup.branding.ui.scorehistory.request;

import lombok.Getter;

@Getter
public class ScoreCancelRequest {
    private Long scoreHistoryId;
    private String memo;
}
