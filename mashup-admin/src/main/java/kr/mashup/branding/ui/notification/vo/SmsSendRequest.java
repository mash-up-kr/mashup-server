package kr.mashup.branding.ui.notification.vo;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import kr.mashup.branding.domain.notification.sms.vo.SmsSendRequestVo;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class SmsSendRequest {
    @ApiModelProperty(value = "발송 메모", example = "스프링팀 서류 합격 문자 안내")
    private String name;

    @ApiModelProperty(value = "문자 내용", example = "합격을 축하행~!")
    private String content;

    @ApiModelProperty(value = "문자 받을 지원자 ID 목록", example = "[1, 2, 3]")
    private List<Long> applicantIds;

}
