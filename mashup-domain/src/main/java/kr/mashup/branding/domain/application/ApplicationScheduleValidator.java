package kr.mashup.branding.domain.application;

import kr.mashup.branding.domain.generation.Generation;

import java.time.LocalDateTime;

public interface ApplicationScheduleValidator {
    void validate(Generation generation, LocalDateTime localDateTime);
}
