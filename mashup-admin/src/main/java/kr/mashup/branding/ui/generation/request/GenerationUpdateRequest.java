package kr.mashup.branding.ui.generation.request;


import kr.mashup.branding.service.generation.vo.GenerationUpdateVo;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
public class GenerationUpdateRequest {

    @NotNull
    private Long generationId;

    @NotNull
    private LocalDate statedAt;

    @NotNull
    private LocalDate endedAt;

    public GenerationUpdateVo toVo(){
        return GenerationUpdateVo.of(generationId, statedAt, endedAt);
    }
}
