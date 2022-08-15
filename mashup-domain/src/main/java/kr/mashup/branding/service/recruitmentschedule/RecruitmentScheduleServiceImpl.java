package kr.mashup.branding.service.recruitmentschedule;

import java.time.LocalDateTime;
import java.util.List;

import kr.mashup.branding.domain.recruitmentschedule.RecruitmentSchedule;
import kr.mashup.branding.domain.recruitmentschedule.RecruitmentScheduleCreateVo;
import kr.mashup.branding.domain.recruitmentschedule.RecruitmentScheduleDuplicatedException;
import kr.mashup.branding.domain.recruitmentschedule.RecruitmentScheduleNotFoundException;
import kr.mashup.branding.domain.recruitmentschedule.RecruitmentScheduleUpdateVo;
import kr.mashup.branding.repository.recruitmentschedule.RecruitmentScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecruitmentScheduleServiceImpl implements RecruitmentScheduleService {
    private static final String RECRUITMENT_STARTED = "RECRUITMENT_STARTED";
    private static final String RECRUITMENT_ENDED = "RECRUITMENT_ENDED";
    private static final String SCREENING_RESULT_ANNOUNCED = "SCREENING_RESULT_ANNOUNCED";
    private static final String INTERVIEW_RESULT_ANNOUNCED = "INTERVIEW_RESULT_ANNOUNCED";

    private final RecruitmentScheduleRepository recruitmentScheduleRepository;

    @Override
    public List<RecruitmentSchedule> getAll() {
        return recruitmentScheduleRepository.findAll();
    }

    @Override
    public RecruitmentSchedule getByEventName(String eventName) {
        return recruitmentScheduleRepository.findByEventName(eventName)
            .orElseThrow(RecruitmentScheduleNotFoundException::new);
    }

    @Override
    @Transactional
    public RecruitmentSchedule create(RecruitmentScheduleCreateVo recruitmentScheduleCreateVo) {
        Assert.notNull(recruitmentScheduleCreateVo, "'createRecruitmentScheduleVo' must not be null");

        if (recruitmentScheduleRepository.existsByEventName(recruitmentScheduleCreateVo.getEventName())) {
            throw new RecruitmentScheduleDuplicatedException(
                "'eventName' is already in use. eventName: " + recruitmentScheduleCreateVo.getEventName());
        }
        return recruitmentScheduleRepository.save(
            RecruitmentSchedule.from(recruitmentScheduleCreateVo)
        );
    }

    @Override
    @Transactional
    public RecruitmentSchedule update(
        Long recruitmentScheduleId,
        RecruitmentScheduleUpdateVo recruitmentScheduleUpdateVo
    ) {
        Assert.notNull(recruitmentScheduleId, "'recruitmentScheduleId' must not be null");
        Assert.notNull(recruitmentScheduleUpdateVo, "'updateRecruitmentScheduleVo' must not be null");

        return recruitmentScheduleRepository.findById(recruitmentScheduleId)
            .map(it -> it.update(recruitmentScheduleUpdateVo))
            .orElseThrow(RecruitmentScheduleNotFoundException::new);
    }

    @Override
    @Transactional
    public void delete(Long recruitmentScheduleId) {
        recruitmentScheduleRepository.findById(recruitmentScheduleId)
            .ifPresent(recruitmentScheduleRepository::delete);
    }

    @Override
    public boolean isRecruitStarted(LocalDateTime localDateTime) {
        LocalDateTime recruitStartedAt = recruitmentScheduleRepository.findByEventName(RECRUITMENT_STARTED)
            .map(RecruitmentSchedule::getEventOccurredAt)
            .orElseThrow(RecruitmentScheduleNotFoundException::new);
        return !localDateTime.isBefore(recruitStartedAt);
    }

    @Override
    public boolean isRecruitAvailable(LocalDateTime localDateTime) {
        LocalDateTime recruitStartedAt = recruitmentScheduleRepository.findByEventName(RECRUITMENT_STARTED)
            .map(RecruitmentSchedule::getEventOccurredAt)
            .orElseThrow(RecruitmentScheduleNotFoundException::new);
        LocalDateTime recruitEndedAt = recruitmentScheduleRepository.findByEventName(RECRUITMENT_ENDED)
            .map(RecruitmentSchedule::getEventOccurredAt)
            .orElseThrow(RecruitmentScheduleNotFoundException::new);
        return !localDateTime.isBefore(recruitStartedAt) && !localDateTime.isAfter(recruitEndedAt);
    }

    @Override
    public boolean canAnnounceScreeningResult(LocalDateTime localDateTime) {
        LocalDateTime screeningResultAnnouncedAt = recruitmentScheduleRepository.findByEventName(
                SCREENING_RESULT_ANNOUNCED)
            .map(RecruitmentSchedule::getEventOccurredAt)
            .orElseThrow(RecruitmentScheduleNotFoundException::new);
        return !localDateTime.isBefore(screeningResultAnnouncedAt);
    }

    @Override
    public boolean canAnnounceInterviewResult(LocalDateTime localDateTime) {
        LocalDateTime interviewResultAnnouncedAt = recruitmentScheduleRepository.findByEventName(
                INTERVIEW_RESULT_ANNOUNCED)
            .map(RecruitmentSchedule::getEventOccurredAt)
            .orElseThrow(RecruitmentScheduleNotFoundException::new);
        return !localDateTime.isBefore(interviewResultAnnouncedAt);
    }
}
