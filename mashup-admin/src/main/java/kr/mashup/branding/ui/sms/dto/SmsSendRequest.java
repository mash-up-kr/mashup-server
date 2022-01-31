package kr.mashup.branding.ui.sms.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SmsSendRequest {
    @ApiModelProperty(value = "요청 그룹에 대한 설명", example = "스프링팀 서류 합격 문자 안내")
    private String description;

    @ApiModelProperty(value = "문자 내용", example = "합격을 축하행~!")
    private String content;

    @ApiModelProperty(value = "문자 받을 유저 ID", example = "[1, 2, 3]")
    private List<Long> applicantIds;
}
