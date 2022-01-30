package kr.mashup.branding.ui.application;

import lombok.Data;

@Data
public class CreateApplicationRequest {
    private final Long teamId;
    // 이전꺼 무시하고 생성
    // 혹은 기본동작( 이전꺼 있으면 에러)
}
