package kr.mashup.branding.domain.application.progress;

import lombok.Value;

@Value(staticConstructor = "of")
public class UpdateApplicationProgressVo {
    Long applicationId;
    ApplicationProgressStatus status;
}
