package kr.mashup.branding.dto.generation;

import kr.mashup.branding.domain.generation.Generation;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor(staticName = "of")
public class GenerationDto {

    private Integer number;

    private LocalDate startedAt;

    private LocalDate endedAt;

    public static GenerationDto from(Generation generation){
        return GenerationDto.of(generation.getNumber(), generation.getStartedAt(), generation.getEndedAt());
    }
}
