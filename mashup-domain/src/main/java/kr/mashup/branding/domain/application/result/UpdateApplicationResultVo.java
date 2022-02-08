package kr.mashup.branding.domain.application.result;

import java.time.LocalDateTime;

import lombok.Value;

@Value(staticConstructor = "of")
public class UpdateApplicationResultVo {
    ApplicationResultStatus status;
    LocalDateTime interviewStartedAt;
    LocalDateTime interviewEndedAt;
}
