package kr.mashup.branding.ui.schedule.request;

import kr.mashup.branding.domain.schedule.ContentsCreateDto;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@ToString
public class ContentsCreateRequest {

    @NotBlank
    private String title;

    private String desc; //optional

    @NotNull
    private LocalDateTime startedAt;

    public ContentsCreateDto toDto(){
        return ContentsCreateDto.of(title, desc, startedAt);
    }
}
