package kr.mashup.branding.ui.sms.dto;

import io.swagger.annotations.ApiModelProperty;
import kr.mashup.branding.domain.sms.SmsRequest;
import kr.mashup.branding.domain.sms.SmsRequestStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SmsRequestResponse {
    @ApiModelProperty(value = "SmsRequest 의 ID", example = "1")
    private Long smsRequestId;

    @ApiModelProperty(value = "요청 상태(발송중, 성공, 실패)", example = "성공")
    private SmsRequestStatus status;

    @ApiModelProperty(value = "이름", example = "홍길동")
    private String applicantName;

    @ApiModelProperty(value = "전화번호", example = "01000000000")
    private String phoneNumber;

    @ApiModelProperty(value = "발송자", example = "이정원")
    private String sender;

    @ApiModelProperty(value = "지원 플랫폼", example = "스프링")
    private String teamName;

    public static SmsRequestResponse of(SmsRequest smsRequest){
        return new SmsRequestResponse(
                smsRequest.getSmsRequestId(),
                smsRequest.getStatus(),
                smsRequest.getApplicantName(),
                smsRequest.getPhoneNumber(),
                "발송자",
                "스프링"
        );
    }
}
