package kr.mashup.branding.ui.sms.dto;

import io.swagger.annotations.ApiModelProperty;
import kr.mashup.branding.domain.sms.SmsRequestGroup;
import kr.mashup.branding.domain.sms.SmsRequestGroupStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SmsRequestGroupResponse {
    @ApiModelProperty(value = "ID", example = "1")
    private Long id;

    @ApiModelProperty(value = "요청 그룹 상태(발송중, 완료)", example = "IN_PROGRESS")
    private SmsRequestGroupStatus status;

    @ApiModelProperty(value = "요청 그룹에 대한 설명", example = "스프링팀 서류 합격 문자 안내")
    private String description;

    @ApiModelProperty(value = "문자 내용", example = "합격을 축하행~!")
    private String content;

    public static SmsRequestGroupResponse of(SmsRequestGroup smsRequestGroup){
        return new SmsRequestGroupResponse(
                smsRequestGroup.getId(),
                smsRequestGroup.getStatus(),
                smsRequestGroup.getDescription(),
                smsRequestGroup.getContent()
        );
    }
}
