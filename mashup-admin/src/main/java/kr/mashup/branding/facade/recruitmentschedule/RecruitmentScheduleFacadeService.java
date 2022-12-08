package kr.mashup.branding.facade.recruitmentschedule;

import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.recruitmentschedule.RecruitmentSchedule;
import kr.mashup.branding.domain.recruitmentschedule.vo.RecruitmentScheduleCreateVo;
import kr.mashup.branding.domain.recruitmentschedule.vo.RecruitmentScheduleUpdateVo;
import kr.mashup.branding.service.generation.GenerationService;
import kr.mashup.branding.service.recruitmentschedule.RecruitmentScheduleService;
import kr.mashup.branding.ui.recruitmentschedule.RecruitmentScheduleCreateRequest;
import kr.mashup.branding.ui.recruitmentschedule.RecruitmentScheduleResponse;
import kr.mashup.branding.ui.recruitmentschedule.RecruitmentScheduleUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecruitmentScheduleFacadeService {
    private final GenerationService generationService;
    private final RecruitmentScheduleService recruitmentScheduleService;

    public List<RecruitmentScheduleResponse> getAll(Integer generationNumber) {
        final Generation generation = generationService.getByNumberOrThrow(generationNumber);
        final List<RecruitmentSchedule> recruitmentSchedules = recruitmentScheduleService.getAll(generation);

        return recruitmentSchedules
            .stream()
            .map(RecruitmentScheduleResponse::from)
            .collect(Collectors.toList());
    }

    @Transactional
    public RecruitmentScheduleResponse create(
        Integer generationNumber,
        RecruitmentScheduleCreateRequest request) {

        final Generation generation = generationService.getByNumberOrThrow(generationNumber);
        final RecruitmentScheduleCreateVo recruitmentScheduleCreateVo = request.toVo();

        final RecruitmentSchedule recruitmentSchedule
            = recruitmentScheduleService.create(generation, recruitmentScheduleCreateVo);

        return RecruitmentScheduleResponse.from(recruitmentSchedule);
    }
    @Transactional
    public RecruitmentScheduleResponse update(
        Long recruitmentScheduleId,
        RecruitmentScheduleUpdateRequest request
    ) {
        final RecruitmentScheduleUpdateVo recruitmentScheduleUpdateVo = request.toVo();
        final RecruitmentSchedule recruitmentSchedule
            = recruitmentScheduleService.update(recruitmentScheduleId, recruitmentScheduleUpdateVo);

        return RecruitmentScheduleResponse.from(recruitmentSchedule);
    }

    @Transactional
    public void delete(Long recruitmentScheduleId) {
        recruitmentScheduleService.delete(recruitmentScheduleId);
    }
}
