package kr.mashup.branding.domain.schedule;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ContentsCreateDto {

    private final String title;

    private final String description;

    private final LocalDateTime startedAt;

    private ContentsCreateDto(String title, String description, LocalDateTime startedAt) {
        this.title = title;
        this.description = description;
        this.startedAt = startedAt;
    }

    public static ContentsCreateDto of(String title, String description, LocalDateTime startedAt) {
        return new ContentsCreateDto(title, description, startedAt);
    }
}
