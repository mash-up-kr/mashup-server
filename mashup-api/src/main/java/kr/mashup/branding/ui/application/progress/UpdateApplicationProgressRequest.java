package kr.mashup.branding.ui.application.progress;

import io.swagger.annotations.ApiModelProperty;
import kr.mashup.branding.domain.application.progress.ApplicationProgressStatus;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UpdateApplicationProgressRequest {
    @ApiModelProperty(value = "지원서 진행 상태", example = "WAIT_CONFIRM_INTERVIEW")
    private ApplicationProgressStatus status;
}
