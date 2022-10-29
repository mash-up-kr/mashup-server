package kr.mashup.branding.domain.schedule;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ContentsCreateDto {

    private final String title;

    private final String desc;

    private final LocalDateTime startedAt;

    public static ContentsCreateDto of(String title, String desc, LocalDateTime startedAt) {
        return new ContentsCreateDto(title, desc, startedAt);
    }

    private ContentsCreateDto(String title, String desc, LocalDateTime startedAt) {
        this.title = title;
        this.desc = desc;
        this.startedAt = startedAt;
    }
}
