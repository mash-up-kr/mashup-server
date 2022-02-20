package kr.mashup.branding.domain.application;

import java.time.LocalDateTime;

public interface ApplicationScheduleValidator {
    void validate(LocalDateTime localDateTime);
}
