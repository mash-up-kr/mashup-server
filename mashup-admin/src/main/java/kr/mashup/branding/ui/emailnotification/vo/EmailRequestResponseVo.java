package kr.mashup.branding.ui.emailnotification.vo;

import io.swagger.annotations.ApiModelProperty;
import kr.mashup.branding.domain.email.EmailRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EmailRequestResponseVo {
    @ApiModelProperty(value = "이메일 발송 상세 내역 식별자", example = "1")
    private Long emailRequestId;

    @ApiModelProperty(value = "수신자 이름", example = "홍길동")
    private String recipientName;

    @ApiModelProperty(value = "수신자 이메일", example = "test@gmail.com")
    private String recipientEmail;

    public static EmailRequestResponseVo of(EmailRequest emailRequest) {
        return EmailRequestResponseVo.builder()
                .emailRequestId(emailRequest.getId())
                .recipientName(emailRequest.getApplication().getApplicant().getName())
                .recipientEmail(emailRequest.getApplication().getApplicant().getEmail())
                .build();
    }
}
