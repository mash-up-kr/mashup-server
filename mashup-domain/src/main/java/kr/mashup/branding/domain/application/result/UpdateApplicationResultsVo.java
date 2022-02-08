package kr.mashup.branding.domain.application.result;

import lombok.Value;

@Value(staticConstructor = "of")
public class UpdateApplicationResultsVo {
    Long applicationId;
    ApplicationResultStatus status;
}
