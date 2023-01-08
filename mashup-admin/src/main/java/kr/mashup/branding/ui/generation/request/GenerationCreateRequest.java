package kr.mashup.branding.ui.generation.request;

import kr.mashup.branding.service.generation.vo.GenerationCreateVo;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@ToString
public class GenerationCreateRequest {

    @NotNull
    private Integer generationNumber;

    @NotNull
    private LocalDate statedAt;

    @NotNull
    private LocalDate endedAt;

    public GenerationCreateVo toVo(){
        return GenerationCreateVo.of(generationNumber, statedAt, endedAt);
    }

}
