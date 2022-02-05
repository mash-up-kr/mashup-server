package kr.mashup.branding.ui.application.progress;

import io.swagger.annotations.ApiModelProperty;
import kr.mashup.branding.domain.application.progress.ApplicationProgressStatus;
import lombok.Data;

@Data
public class ApplicationProgressResponse {
    @ApiModelProperty(value = "Application 의 ID", example = "1")
    private final Long ApplicationId;
    @ApiModelProperty(value = "지원서 진행 상태", example = "WAIT_CONFIRM_INTERVIEW")
    private final ApplicationProgressStatus status;
}
