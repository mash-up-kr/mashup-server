package kr.mashup.branding.domain.application;

import java.time.LocalDateTime;

import kr.mashup.branding.domain.generation.Generation;
import org.springframework.stereotype.Component;

import kr.mashup.branding.service.recruitmentschedule.RecruitmentScheduleService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ApplicationScheduleValidatorImpl implements ApplicationScheduleValidator {
    private final RecruitmentScheduleService recruitmentScheduleService;

    @Override
    public void validate(Generation generation, LocalDateTime localDateTime) {
        if (!recruitmentScheduleService.isRecruitAvailable(generation, localDateTime)) {
            throw new ApplicationModificationNotAllowedException("지원서 제출 기간이 아닙니다.");
        }
    }
}
