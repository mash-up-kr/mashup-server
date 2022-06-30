package kr.mashup.branding.service.schedule;

import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.exception.NotFoundException;
import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.schedule.Schedule;
import kr.mashup.branding.repository.schedule.ScheduleRepository;
import kr.mashup.branding.service.generation.GenerationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final GenerationService generationService;

    @Transactional
    public Schedule save(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    @Transactional(readOnly = true)
    public Schedule getByIdOrThrow(Long scheduleId) {
        return scheduleRepository.findById(scheduleId)
            .orElseThrow(() -> new NotFoundException(ResultCode.SCHEDULE_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public List<Schedule> getByGeneration(Generation generation) {
        return scheduleRepository.findByGeneration(
            generation,
            Sort.by(Sort.Direction.ASC, "startedAt")
        );
    }
}
