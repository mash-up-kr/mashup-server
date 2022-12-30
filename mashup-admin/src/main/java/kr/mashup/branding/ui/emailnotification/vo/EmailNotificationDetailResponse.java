package kr.mashup.branding.ui.emailnotification.vo;

import io.swagger.annotations.ApiModelProperty;
import kr.mashup.branding.domain.adminmember.entity.Position;
import kr.mashup.branding.domain.email.EmailNotification;
import kr.mashup.branding.domain.email.EmailRequest;
import kr.mashup.branding.domain.email.EmailRequestStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Builder
public class EmailNotificationDetailResponse {
    @ApiModelProperty(value = "이메일 발송 내역 식별자", example = "1")
    private Long emailNotificationId;

    @ApiModelProperty(value = "발송 메모", example = "지원 완료 이메일 안내")
    private String name;

    @ApiModelProperty(value = "발송 유형", example = "서류 결과 발표")
    private String type;

    @ApiModelProperty(value = "발송 시각")
    private LocalDateTime sendAt;

    @ApiModelProperty(value = "발송자")
    private Position sender;

    @ApiModelProperty(value = "이메일 발신 목록")
    private List<EmailRequestResponse> emailRequests;

    @ApiModelProperty(value = "발송 성공한 수신자 수", example = "209")
    private Long successCount;

    @ApiModelProperty(value = "발송 실패한 수신자 수", example = "11")
    private Long failureCount;

    @ApiModelProperty(value = "전체 수신자 수", example = "220")
    private Long totalCount;

    public static EmailNotificationDetailResponse of(
        final EmailNotification emailNotification
    ) {

        final List<EmailRequest> emailRequests = emailNotification.getEmailRequests();

        final List<EmailRequestResponse> emailRequestResponses
            = emailRequests
            .stream()
            .map(EmailRequestResponse::of)
            .collect(Collectors.toList());

        final Map<EmailRequestStatus, Long> statusCountMap = emailRequests.
            stream()
            .collect(Collectors.groupingBy(EmailRequest::getStatus, Collectors.counting()));

        final Long successCount = statusCountMap.getOrDefault(EmailRequestStatus.SUCCESS, 0L);
        final Long failureCount = statusCountMap.getOrDefault(EmailRequestStatus.FAIL, 0L);


        return EmailNotificationDetailResponse.builder()
            .emailNotificationId(emailNotification.getId())
            .name(emailNotification.getMemo())
            .type(emailNotification.getEmailTemplateName().name())
            .sender(emailNotification.getSender().getPosition())
            .emailRequests(emailRequestResponses)
            .successCount(successCount)
            .failureCount(failureCount)
            .totalCount(successCount + failureCount)
            .sendAt(emailNotification.getCreatedAt())
            .build();
    }
}
