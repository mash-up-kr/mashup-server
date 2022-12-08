package kr.mashup.branding.service.recruitmentschedule;

import java.time.LocalDateTime;
import java.util.List;

import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.recruitmentschedule.RecruitmentSchedule;
import kr.mashup.branding.domain.recruitmentschedule.vo.RecruitmentScheduleCreateVo;
import kr.mashup.branding.domain.recruitmentschedule.exception.RecruitmentScheduleDuplicatedException;
import kr.mashup.branding.domain.recruitmentschedule.RecruitmentScheduleEventName;
import kr.mashup.branding.domain.recruitmentschedule.exception.RecruitmentScheduleNotFoundException;
import kr.mashup.branding.domain.recruitmentschedule.vo.RecruitmentScheduleUpdateVo;
import kr.mashup.branding.repository.recruitmentschedule.RecruitmentScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import lombok.RequiredArgsConstructor;

import static kr.mashup.branding.domain.recruitmentschedule.RecruitmentScheduleEventName.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecruitmentScheduleService {

    private final RecruitmentScheduleRepository recruitmentScheduleRepository;

    /**
     * 채용 일정 목록 조회
     */
    public List<RecruitmentSchedule> getAll(final Generation generation) {
        return recruitmentScheduleRepository.findAllByGeneration(generation);
    }

    /**
     * 채용 일정 생성
     */
    @Transactional
    public RecruitmentSchedule create(
        final Generation generation,
        final RecruitmentScheduleCreateVo recruitmentScheduleCreateVo) {
        Assert.notNull(recruitmentScheduleCreateVo, "'createRecruitmentScheduleVo' must not be null");

        if (recruitmentScheduleRepository.
            existsByGenerationAndEventName(generation, recruitmentScheduleCreateVo.getEventName())) {

            throw new RecruitmentScheduleDuplicatedException(
                "'eventName' is already in use. eventName: " + recruitmentScheduleCreateVo.getEventName());
        }
        return recruitmentScheduleRepository.save(
            RecruitmentSchedule.from(recruitmentScheduleCreateVo)
        );
    }

    /**
     * 채용 일정 변경
     */
    @Transactional
    public RecruitmentSchedule update(
        final Long recruitmentScheduleId,
        final RecruitmentScheduleUpdateVo recruitmentScheduleUpdateVo
    ) {
        Assert.notNull(recruitmentScheduleId, "'recruitmentScheduleId' must not be null");
        Assert.notNull(recruitmentScheduleUpdateVo, "'updateRecruitmentScheduleVo' must not be null");

        return recruitmentScheduleRepository.findById(recruitmentScheduleId)
            .map(it -> it.update(recruitmentScheduleUpdateVo))
            .orElseThrow(RecruitmentScheduleNotFoundException::new);
    }

    /**
     * 채용 일정 삭제
     */
    @Transactional
    public void delete(Long recruitmentScheduleId) {
        recruitmentScheduleRepository
            .findById(recruitmentScheduleId)
            .ifPresent(recruitmentScheduleRepository::delete);
    }

    /**
     * 모집 시작했는지
     */
    public boolean isRecruitStarted(Generation generation, LocalDateTime localDateTime) {
        final LocalDateTime recruitStartedAt
            = recruitmentScheduleRepository.findByEventName(generation, RECRUITMENT_STARTED)
            .map(RecruitmentSchedule::getEventOccurredAt)
            .orElseThrow(RecruitmentScheduleNotFoundException::new);

        return !localDateTime.isBefore(recruitStartedAt);
    }

    /**
     * 서류 제출 가능한 시각인지
     */
    public boolean isRecruitAvailable(Generation generation, LocalDateTime localDateTime) {
        final LocalDateTime recruitStartedAt
            = recruitmentScheduleRepository.findByEventName(generation, RECRUITMENT_STARTED)
            .map(RecruitmentSchedule::getEventOccurredAt)
            .orElseThrow(RecruitmentScheduleNotFoundException::new);

        final LocalDateTime recruitEndedAt = recruitmentScheduleRepository.findByEventName(generation, RECRUITMENT_ENDED)
            .map(RecruitmentSchedule::getEventOccurredAt)
            .orElseThrow(RecruitmentScheduleNotFoundException::new);

        return !localDateTime.isBefore(recruitStartedAt) && !localDateTime.isAfter(recruitEndedAt);
    }

    /**
     * 서류 결과 보여주어도 되는 시각인지
     */
    public boolean canAnnounceScreeningResult(Generation generation, LocalDateTime localDateTime) {

        final LocalDateTime screeningResultAnnouncedAt
            = recruitmentScheduleRepository.findByEventName(generation, SCREENING_RESULT_ANNOUNCED)
            .map(RecruitmentSchedule::getEventOccurredAt)
            .orElseThrow(RecruitmentScheduleNotFoundException::new);

        return !localDateTime.isBefore(screeningResultAnnouncedAt);
    }

    /**
     * 면접 결과 보여주어도 되는 시각인지
     */
    public boolean canAnnounceInterviewResult(Generation generation, LocalDateTime localDateTime) {

        final LocalDateTime interviewResultAnnouncedAt
            = recruitmentScheduleRepository.findByEventName(generation, INTERVIEW_RESULT_ANNOUNCED)
            .map(RecruitmentSchedule::getEventOccurredAt)
            .orElseThrow(RecruitmentScheduleNotFoundException::new);

        return !localDateTime.isBefore(interviewResultAnnouncedAt);
    }

    public RecruitmentSchedule getByEventName(Generation generation, RecruitmentScheduleEventName eventName) {
        return recruitmentScheduleRepository
            .findByEventName(generation, eventName)
            .orElseThrow(RecruitmentScheduleNotFoundException::new);
    }

}
