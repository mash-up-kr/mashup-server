package kr.mashup.branding.ui.notification;

import io.swagger.annotations.ApiModelProperty;
import kr.mashup.branding.domain.notification.NotificationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotificationSimpleResponse {
    @ApiModelProperty(value = "발송 내역 식별자", example = "1")
    private Long notificationId;

    @ApiModelProperty(value = "발송 내역 상태(생성됨, 알수없음, 진행중, 성공, 실패)")
    private NotificationStatus status;

    @ApiModelProperty(value = "발송 메모", example = "스프링팀 서류 합격 문자 안내")
    private String name;

    @ApiModelProperty(value = "문자 내용", example = "합격을 축하행~!")
    private String content;
}
