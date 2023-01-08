package kr.mashup.branding.ui.emailnotification.vo;

import io.swagger.annotations.ApiModelProperty;
import kr.mashup.branding.domain.applicant.Applicant;
import kr.mashup.branding.domain.application.Application;
import kr.mashup.branding.domain.email.EmailRequest;
import kr.mashup.branding.domain.email.EmailRequestStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EmailRequestResponse {
    @ApiModelProperty(value = "이메일 발송 상세 내역 식별자", example = "1")
    private Long emailRequestId;

    @ApiModelProperty(value = "수신자 이름", example = "홍길동")
    private String recipientName;

    @ApiModelProperty(value = "수신자 이메일", example = "test@gmail.com")
    private String recipientEmail;

    @ApiModelProperty(value = "지원서 아이디", example = "1")
    private Long applicationId;

    @ApiModelProperty(value = "지원 플랫폼", example = "디자인")
    private String team;

    @ApiModelProperty(value = "발송 상태", example = "SUCCESS")
    private EmailRequestStatus status;

    public static EmailRequestResponse of(
        final EmailRequest emailRequest
    ) {
        final Application application = emailRequest.getApplication();
        final Applicant applicant = application.getApplicant();

        return EmailRequestResponse.builder()
                .emailRequestId(emailRequest.getId())
                .recipientName(applicant.getName())
                .recipientEmail(applicant.getEmail())
                .applicationId(emailRequest.getApplication().getApplicationId())
                .team(application.getApplicationForm().getTeam().getName())
                .status(emailRequest.getStatus())
                .build();
    }
}
