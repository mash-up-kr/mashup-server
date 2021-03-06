package kr.mashup.branding.domain.application;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import kr.mashup.branding.service.recruitmentschedule.RecruitmentScheduleService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ApplicationScheduleValidatorImpl implements ApplicationScheduleValidator {
    private final RecruitmentScheduleService recruitmentScheduleService;

    @Override
    public void validate(LocalDateTime localDateTime) {
        if (!recruitmentScheduleService.isRecruitAvailable(localDateTime)) {
            throw new ApplicationModificationNotAllowedException("지원서 제출 기간이 아닙니다.");
        }
    }
}
