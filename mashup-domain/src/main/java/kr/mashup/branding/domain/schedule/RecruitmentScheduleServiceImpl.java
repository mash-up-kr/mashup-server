package kr.mashup.branding.domain.schedule;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecruitmentScheduleServiceImpl implements RecruitmentScheduleService {
    private final RecruitmentScheduleRepository recruitmentScheduleRepository;

    @Override
    public boolean isRecruitAvailable(LocalDateTime localDateTime) {
        LocalDateTime recruitStartedAt = recruitmentScheduleRepository.findByEventName("RECRUITMENT_STARTED")
            .map(RecruitmentSchedule::getEventOccurredAt)
            .orElseThrow(RecruitmentScheduleNotFoundException::new);
        LocalDateTime recruitEndedAt = recruitmentScheduleRepository.findByEventName("RECRUITMENT_ENDED")
            .map(RecruitmentSchedule::getEventOccurredAt)
            .orElseThrow(RecruitmentScheduleNotFoundException::new);
        return !localDateTime.isBefore(recruitStartedAt) && !localDateTime.isAfter(recruitEndedAt);
    }

    @Override
    public boolean canAnnounceScreeningResult(LocalDateTime localDateTime) {
        LocalDateTime screeningResultAnnouncedAt = recruitmentScheduleRepository.findByEventName(
            "SCREENING_RESULT_ANNOUNCED")
            .map(RecruitmentSchedule::getEventOccurredAt)
            .orElseThrow(RecruitmentScheduleNotFoundException::new);
        return !localDateTime.isBefore(screeningResultAnnouncedAt);
    }

    @Override
    public boolean canAnnounceInterviewResult(LocalDateTime localDateTime) {
        LocalDateTime interviewResultAnnouncedAt = recruitmentScheduleRepository.findByEventName(
            "INTERVIEW_RESULT_ANNOUNCED")
            .map(RecruitmentSchedule::getEventOccurredAt)
            .orElseThrow(RecruitmentScheduleNotFoundException::new);
        return !localDateTime.isBefore(interviewResultAnnouncedAt);
    }
}
