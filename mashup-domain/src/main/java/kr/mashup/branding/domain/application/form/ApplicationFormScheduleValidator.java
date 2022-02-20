package kr.mashup.branding.domain.application.form;

import java.time.LocalDateTime;

public interface ApplicationFormScheduleValidator {
    void validate(LocalDateTime localDateTime);
}
