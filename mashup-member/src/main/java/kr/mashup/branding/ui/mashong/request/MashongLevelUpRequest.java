package kr.mashup.branding.ui.mashong.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
public class MashongLevelUpRequest {

    @NotNull(message = "레벨업 목표 레벨은 비어있을 수 없습니다.")
    @Min(value = 1, message = "레벨업 목표 레벨은 1 이상이어야 합니다.")
    private int goalLevel;
}
