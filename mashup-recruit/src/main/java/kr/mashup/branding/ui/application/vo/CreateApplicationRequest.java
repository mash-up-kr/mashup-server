package kr.mashup.branding.ui.application.vo;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CreateApplicationRequest {
    private Long teamId;
    // 이전꺼 무시하고 생성
    // 혹은 기본동작( 이전꺼 있으면 에러)
}
