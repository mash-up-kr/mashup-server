package kr.mashup.branding.ui.sms.dto;

import io.swagger.annotations.ApiModelProperty;
import kr.mashup.branding.domain.sms.SmsRequest;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SmsRequestResponse {
    @ApiModelProperty(value = "ID", example = "1")
    private Long id;

    @ApiModelProperty(value = "요청 상태(발송중, 성공, 실패)", example = "성공")
    private String status;

    @ApiModelProperty(value = "이름", example = "홍길동")
    private String name;

    @ApiModelProperty(value = "전화번호", example = "01000000000")
    private String phone;

    @ApiModelProperty(value = "발송자", example = "이정원")
    private String sender;

    @ApiModelProperty(value = "지원 플랫폼", example = "스프링")
    private String platform;

    public static SmsRequestResponse of(SmsRequest smsRequest){
        return new SmsRequestResponse(
                smsRequest.getId(),
                smsRequest.getStatus().getDescription(),
                smsRequest.getUsername(),
                smsRequest.getPhoneNumber(),
                "발송자",
                "스프링"
        );
    }
}
