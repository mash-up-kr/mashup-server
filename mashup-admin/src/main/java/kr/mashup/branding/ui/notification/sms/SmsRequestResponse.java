package kr.mashup.branding.ui.notification.sms;

import io.swagger.annotations.ApiModelProperty;
import kr.mashup.branding.domain.notification.sms.SmsNotificationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SmsRequestResponse {
    @ApiModelProperty(value = "SmsRequest 의 ID", example = "1")
    private Long smsRequestId;

    @ApiModelProperty(value = "발송 내역 식별자", example = "1")
    private Long notificationId;

    @ApiModelProperty(value = "요청 상태(발송중, 성공, 실패)", example = "성공")
    private SmsNotificationStatus status;

    @ApiModelProperty(value = "수신자 이름", example = "홍길동")
    private String recipientName;

    @ApiModelProperty(value = "수신자 전화번호", example = "01000000000")
    private String recipientPhoneNumber;

    @ApiModelProperty(value = "지원 플랫폼", example = "스프링")
    private String teamName;
}
