package kr.mashup.branding.domain.application.confirmation;

import lombok.Value;

@Value(staticConstructor = "of")
public class UpdateConfirmationVo {
    Long applicationId;
    ApplicantConfirmationStatus status;
}
