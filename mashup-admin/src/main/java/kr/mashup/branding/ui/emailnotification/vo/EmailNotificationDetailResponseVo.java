package kr.mashup.branding.ui.emailnotification.vo;

import io.swagger.annotations.ApiModelProperty;
import kr.mashup.branding.domain.adminmember.entity.Position;
import kr.mashup.branding.domain.email.EmailNotification;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
public class EmailNotificationDetailResponseVo {
    @ApiModelProperty(value = "이메일 발송 내역 식별자", example = "1")
    private Long emailNotificationId;

    @ApiModelProperty(value = "발송 메모", example = "지원 완료 이메일 안내")
    private String name;

    @ApiModelProperty(value = "발송 유형", example = "서류 결과 발표")
    private String type;

    //TODO: 발송 시각 어디에 있는지 찾아서 넣기
    @ApiModelProperty(value = "발송 시각")
    private LocalDateTime sendAt;

    @ApiModelProperty(value = "발송자")
    private Position sender;

    @ApiModelProperty(value = "이메일 발신 목록")
    private List<EmailRequestResponseVo> emailRequests;

    public static EmailNotificationDetailResponseVo of(EmailNotification emailNotification) {
        List<EmailRequestResponseVo> emailRequestResponseVos = emailNotification.getEmailRequests()
                .stream().map(EmailRequestResponseVo::of).toList();

        return EmailNotificationDetailResponseVo.builder()
                .emailNotificationId(emailNotification.getId())
                .name(emailNotification.getMemo())
                .type(emailNotification.getEmailTemplate().getTemplateName().name())
                .sender(emailNotification.getSender().getPosition())
                .emailRequests(emailRequestResponseVos)
                .build();
    }
}
