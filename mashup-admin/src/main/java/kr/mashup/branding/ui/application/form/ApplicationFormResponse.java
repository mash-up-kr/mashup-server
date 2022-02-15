package kr.mashup.branding.ui.application.form;

import java.time.LocalDateTime;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApplicationFormResponse {
    @ApiModelProperty(name = "설문지 식별자")
    private Long applicationFormId;
    @ApiModelProperty(name = "설문지 문서 이름")
    private String name;
    @ApiModelProperty(name = "질문 목록")
    private List<QuestionResponse> questions;
    @ApiModelProperty(name = "최초 생성 시각")
    private LocalDateTime createdAt;
    @ApiModelProperty(name = "최초 생성자")
    private String createdBy;
    @ApiModelProperty(name = "마지막 수정 시각")
    private LocalDateTime updatedAt;
    @ApiModelProperty(name = "마지막 수정자")
    private String updatedBy;
}
