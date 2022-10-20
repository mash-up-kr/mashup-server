package kr.mashup.branding.domain.application.form;

import kr.mashup.branding.domain.generation.Generation;

import java.time.LocalDateTime;

public interface ApplicationFormScheduleValidator {
    void validate(Generation generation, LocalDateTime localDateTime);
}
