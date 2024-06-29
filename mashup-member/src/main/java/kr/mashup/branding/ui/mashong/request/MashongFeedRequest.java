package kr.mashup.branding.ui.mashong.request;

import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
public class MashongFeedRequest {

    @NotNull(message = "먹일 팝콘 개수는 비어있을 수 없습니다.")
    @Min(value = 1, message = "먹일 팝콘 개수는 1 이상이어야 합니다.")
    private Long popcornCount;
}
