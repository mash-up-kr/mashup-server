package kr.mashup.branding.ui.application.vo;

import kr.mashup.branding.domain.generation.Generation;
import lombok.Data;

@Data
public class GenerationResponse {
    private final Long generationId;
    private final Integer generationNumber;

    public static GenerationResponse from(Generation generation){
        return new GenerationResponse(generation.getId(), generation.getNumber());
    }

}
