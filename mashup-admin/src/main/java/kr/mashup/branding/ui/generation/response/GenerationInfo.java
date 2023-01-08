package kr.mashup.branding.ui.generation.response;

import kr.mashup.branding.domain.generation.Generation;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GenerationInfo {

    private Long generationId;
    private Integer generationNumber;
    private LocalDate startedAt;
    private LocalDate endedAt;

    public static GenerationInfo from(Generation generation){
        return new GenerationInfo(generation.getId(), generation.getNumber(), generation.getStartedAt(),
            generation.getEndedAt());
    }
}
