package kr.mashup.branding.facade.schedule;

import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.schedule.ContentsCreateDto;
import kr.mashup.branding.domain.schedule.Event;
import kr.mashup.branding.domain.schedule.Schedule;
import kr.mashup.branding.domain.schedule.ScheduleCreateDto;
import kr.mashup.branding.service.generation.GenerationService;
import kr.mashup.branding.service.schedule.EventCreateDto;
import kr.mashup.branding.service.schedule.ScheduleService;
import kr.mashup.branding.ui.schedule.request.ContentsCreateRequest;
import kr.mashup.branding.ui.schedule.request.EventCreateRequest;
import kr.mashup.branding.ui.schedule.request.ScheduleCreateRequest;
import kr.mashup.branding.ui.schedule.request.ScheduleUpdateRequest;
import kr.mashup.branding.ui.schedule.response.ScheduleResponse;
import kr.mashup.branding.util.DateRange;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ScheduleFacadeService {
    private final ScheduleService scheduleService;
    private final GenerationService generationService;

    // TODO: 스케줄 목록 조회

    @Transactional
    public ScheduleResponse create(Integer generationNumber, ScheduleCreateRequest request) {
        final Generation generation =
            generationService.getByNumberOrThrow(generationNumber);
        final DateRange dateRange
            = DateRange.of(request.getStartedAt(), request.getEndedAt());
        final Schedule schedule
            = scheduleService.create(generation, ScheduleCreateDto.of(request.getName(), dateRange));

        final List<EventCreateRequest> eventsCreateRequests
            = request.getEventsCreateRequests();
        for(EventCreateRequest eventCreateRequest : eventsCreateRequests){

            final EventCreateDto eventCreateDto
                = eventCreateRequest.toEventCreateDto();
            final Event event = scheduleService.addEvents(schedule, eventCreateDto);

            final List<ContentsCreateRequest> contentsCreateRequests
                = eventCreateRequest.getContentsCreateRequests();

            for(ContentsCreateRequest contentsCreateRequest : contentsCreateRequests){
                final ContentsCreateDto contentsCreateDto = contentsCreateRequest.toDto();
                scheduleService.addContent(event, contentsCreateDto);
            }
        }

        return ScheduleResponse.from(schedule);
    }

    @Transactional
    public void publishSchedule(Long scheduleId) {
        scheduleService.publishSchedule(scheduleId);
    }

    @Transactional
    public ScheduleResponse updateSchedule(Long scheduleId, ScheduleUpdateRequest scheduleUpdateRequest){
        final Schedule schedule
            = scheduleService.getByIdOrThrow(scheduleId);
        // TODO
        // 배포 취소 상태에만 수정이 가능한가?
    }

    @Transactional
    public void deleteSchedule(Long scheduleId){
        final Schedule schedule
            = scheduleService.getByIdOrThrow(scheduleId);
        // TODO
        // 이미 진행된 스케줄이라면?
    }
}
