package kr.mashup.branding.domain.generation;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor(staticName = "of")
public class GenerationVo {

    private Integer number;

    private LocalDate startedAt;

    private LocalDate endedAt;

    public static GenerationVo from(Generation generation){
        return GenerationVo.of(generation.getNumber(), generation.getStartedAt(), generation.getEndedAt());
    }
}
