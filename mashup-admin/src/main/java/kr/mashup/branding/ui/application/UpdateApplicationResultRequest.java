package kr.mashup.branding.ui.application;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UpdateApplicationResultRequest {
    private String applicationResultStatus;
    private LocalDateTime interviewStartedAt;
    private LocalDateTime interviewEndedAt;
}
