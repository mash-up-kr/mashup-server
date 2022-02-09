package kr.mashup.branding.domain.schedule;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecruitmentScheduleServiceImpl implements RecruitmentScheduleService {
    private final RecruitmentScheduleRepository recruitmentScheduleRepository;

    @Override
    public List<RecruitmentSchedule> getAll() {
        return recruitmentScheduleRepository.findAll();
    }

    @Override
    @Transactional
    public RecruitmentSchedule createOrUpdate(RecruitmentEvent recruitmentEvent) {
        return recruitmentScheduleRepository.findByEventName(recruitmentEvent.getEventName())
            .map(it -> it.update(recruitmentEvent))
            .orElseGet(() -> recruitmentScheduleRepository.save(RecruitmentSchedule.from(recruitmentEvent)));
    }

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
