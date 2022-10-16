package kr.mashup.branding.domain.application.form;

import java.time.LocalDateTime;

import kr.mashup.branding.domain.generation.Generation;
import org.springframework.stereotype.Component;

import kr.mashup.branding.service.recruitmentschedule.RecruitmentScheduleService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ApplicationFormScheduleValidatorImpl implements ApplicationFormScheduleValidator {
    private final RecruitmentScheduleService recruitmentScheduleService;

    /**
     * 지원서 수정 전 검증
     */
    @Override
    public void validate(Generation generation, LocalDateTime localDateTime) {
        if (recruitmentScheduleService.isRecruitStarted(generation, localDateTime)) {
            throw new ApplicationFormModificationNotAllowedException("모집 시작시각 이후에는 지원서를 생성, 수정 및 삭제할 수 없습니다");
        }
    }
}
