package kr.mashup.branding.service.generation.vo;

import com.mysema.commons.lang.Assert;
import kr.mashup.branding.util.DateUtil;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class GenerationUpdateVo {

    private final Long generationId;

    private final LocalDate statedAt;

    private final LocalDate endedAt;

    public static GenerationUpdateVo of(
        Long generationId,
        LocalDate startedAt,
        LocalDate endedAt
    ){
        Assert.notNull(generationId, "generationId must not be null");
        Assert.notNull(startedAt, "startedAt must not be null");
        Assert.notNull(endedAt, "endedAt must not be null");

        if(!DateUtil.isStartBeforeOrEqualEnd(startedAt, endedAt)){
            throw new IllegalArgumentException("startedAt must be before or equal to ended day");
        }

        return new GenerationUpdateVo(generationId, startedAt, endedAt);
    }

    private GenerationUpdateVo(Long generationId, LocalDate statedAt, LocalDate endedAt) {
        this.generationId = generationId;
        this.statedAt = statedAt;
        this.endedAt = endedAt;
    }
}
