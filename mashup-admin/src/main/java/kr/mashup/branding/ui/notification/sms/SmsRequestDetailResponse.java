package kr.mashup.branding.ui.notification.sms;

import io.swagger.annotations.ApiModelProperty;
import kr.mashup.branding.domain.notification.sms.SmsNotificationStatus;
import kr.mashup.branding.ui.team.TeamResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SmsRequestDetailResponse {
    @ApiModelProperty(value = "발송 상세 정보 ID", example = "1")
    private Long smsRequestId;

    @ApiModelProperty(value = "발송 메모", example = "스프링팀 서류 합격 문자 안내")
    private String notificationName;

    @ApiModelProperty(value = "발송 내용", example = "합격을 축하행~!")
    private String notificationContent;

    @ApiModelProperty(value = "발송 상태(성공, 실패)", example = "SUCCESS")
    private SmsNotificationStatus status;

    @ApiModelProperty(value = "수신자 지원 플랫폼")
    private TeamResponse team;
}
