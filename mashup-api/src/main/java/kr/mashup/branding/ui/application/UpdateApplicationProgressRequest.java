package kr.mashup.branding.ui.application;

import io.swagger.annotations.ApiModelProperty;
import kr.mashup.branding.domain.application.progress.ApplicationProgressStatus;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UpdateApplicationProgressRequest {
    @ApiModelProperty(value = "지원자 확인 여부", example = "INTERVIEW_CONFIRM_WAITING")
    private ApplicationProgressStatus status;
}