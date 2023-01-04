package kr.mashup.branding.infrastructure.test;

import java.time.LocalDateTime;

import kr.mashup.branding.domain.generation.Generation;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import kr.mashup.branding.domain.application.form.ApplicationFormScheduleValidator;
import lombok.extern.slf4j.Slf4j;

@Slf4j
//@Profile("develop")
//@Primary
//@Component
public class NoopApplicationFormScheduleValidator implements ApplicationFormScheduleValidator {
    @Override
    public void validate(Generation generation, LocalDateTime localDateTime) {
        log.info("개발환경에서는 설문지 생성, 임시저장, 제출시 채용 일정 관련된 검증을 하지 않습니다.");
    }
}
