package kr.mashup.branding.service.generation.vo;

import com.mysema.commons.lang.Assert;
import kr.mashup.branding.util.DateUtil;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class GenerationCreateVo {

    private final Integer generationNumber;

    private final LocalDate statedAt;

    private final LocalDate endedAt;

    public static GenerationCreateVo of(
        Integer generationNumber,
        LocalDate startedAt,
        LocalDate endedAt
    ){
        Assert.notNull(generationNumber, "generation number must not be null");
        Assert.notNull(startedAt, "startedAt must not be null");
        Assert.notNull(endedAt, "endedAt must not be null");

        if(!DateUtil.isStartBeforeOrEqualEnd(startedAt, endedAt)){
            throw new IllegalArgumentException("startedAt must be before or equal to ended day");
        }

        return new GenerationCreateVo(generationNumber, startedAt, endedAt);
    }

    private GenerationCreateVo(Integer generationNumber, LocalDate statedAt, LocalDate endedAt) {
        this.generationNumber = generationNumber;
        this.statedAt = statedAt;
        this.endedAt = endedAt;
    }
}
