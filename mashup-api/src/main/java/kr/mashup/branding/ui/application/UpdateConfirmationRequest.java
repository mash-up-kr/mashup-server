package kr.mashup.branding.ui.application;

import io.swagger.annotations.ApiModelProperty;
import kr.mashup.branding.domain.application.confirmation.ApplicantConfirmationStatus;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UpdateConfirmationRequest {
    @ApiModelProperty(value = "지원자 확인 여부", example = "INTERVIEW_CONFIRM_ACCEPTED")
    private ApplicantConfirmationStatus status;
}
