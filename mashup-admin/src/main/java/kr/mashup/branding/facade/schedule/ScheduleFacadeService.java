package kr.mashup.branding.facade.schedule;

import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.pushnoti.vo.SeminarUpdatedVo;
import kr.mashup.branding.domain.schedule.*;
import kr.mashup.branding.infrastructure.pushnoti.PushNotiEventPublisher;
import kr.mashup.branding.service.generation.GenerationService;
import kr.mashup.branding.service.member.MemberService;
import kr.mashup.branding.service.schedule.EventCreateDto;
import kr.mashup.branding.service.schedule.ScheduleService;
import kr.mashup.branding.ui.schedule.request.*;
import kr.mashup.branding.ui.schedule.response.QrCodeResponse;
import kr.mashup.branding.ui.schedule.response.ScheduleResponse;
import kr.mashup.branding.util.DateRange;
import kr.mashup.branding.util.QrGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ScheduleFacadeService {
    private final ScheduleService scheduleService;
    private final GenerationService generationService;
    private final PushNotiEventPublisher pushNotiEventPublisher;
    private final MemberService memberService;

    public Page<ScheduleResponse> getSchedules(Integer generationNumber, String searchWord, ScheduleType scheduleType, Pageable pageable) {

        final Generation generation
                = generationService.getByNumberOrThrow(generationNumber);

        final Page<Schedule> schedules = scheduleService.getByGeneration(generation, searchWord, scheduleType, null, pageable);
        final List<ScheduleResponse> scheduleResponses = schedules
                .stream()
                .map(ScheduleResponse::from)
                .collect(Collectors.toList());

        return new PageImpl<>(scheduleResponses, pageable, schedules.getTotalElements());
    }

    public ScheduleResponse getSchedule(final Long scheduleId) {
        final Schedule schedule = scheduleService.getByIdOrThrow(scheduleId);

        return ScheduleResponse.from(schedule);
    }

    @Transactional
    public ScheduleResponse create(Integer generationNumber, ScheduleCreateRequest request) {
        final Generation generation =
                generationService.getByNumberOrThrow(generationNumber);
        final DateRange dateRange
                = DateRange.of(request.getStartedAt(), request.getEndedAt());
        final ScheduleCreateDto createDto =
                ScheduleCreateDto.of(request.getName(), dateRange, request.getLatitude(), request.getLongitude(), request.getRoadAddress(), request.getDetailAddress(), request.getScheduleType());
        final Schedule schedule
                = scheduleService.create(generation, createDto);

        doUpdateSchedule(schedule, request);

        return ScheduleResponse.from(schedule);
    }

    @Transactional
    public void publishSchedule(Long scheduleId) {

        final Schedule schedule
                = scheduleService.getByIdOrThrow(scheduleId);

        scheduleService.publishSchedule(schedule);

        pushNotiEventPublisher.publishPushNotiSendEvent(
                new SeminarUpdatedVo(memberService.getPushNotiTargetableMembersBySchedule(schedule), schedule.getScheduleType())
        );
    }

    @Transactional
    public void hideSchedule(Long scheduleId) {
        final Schedule schedule
                = scheduleService.getByIdOrThrow(scheduleId);
        scheduleService.hideSchedule(schedule);
    }

    @Transactional
    public ScheduleResponse updateSchedule(
            final Long scheduleId,
            final ScheduleUpdateRequest request) {
        Schedule schedule
                = scheduleService.getByIdOrThrow(scheduleId);

        final Generation generation
                = generationService.getByNumberOrThrow(request.getGenerationNumber());

        final DateRange dateRange = DateRange.of(request.getStartedAt(), request.getEndedAt());
        final ScheduleCreateDto scheduleCreateDto =
                ScheduleCreateDto.of(request.getName(), dateRange, request.getLatitude(), request.getLongitude(),
                        request.getRoadAddress(), request.getDetailAddress(), request.getScheduleType());

        schedule = scheduleService.updateSchedule(schedule, generation, scheduleCreateDto);

        schedule.clearEvent();
        doUpdateSchedule(schedule, ScheduleCreateRequest.from(request));

        return ScheduleResponse.from(schedule);
    }

    @Transactional
    public void deleteSchedule(Long scheduleId) {
        final Schedule schedule
                = scheduleService.getByIdOrThrow(scheduleId);

        scheduleService.deleteSchedule(schedule);
    }

    @Transactional
    public QrCodeResponse updateQrCode(Long scheduleId, Long eventId, QrCodeGenerateRequest request) {

        final Schedule schedule
                = scheduleService.getByIdOrThrow(scheduleId);

        final Event event
                = scheduleService.getEventOrThrow(schedule, eventId);

        final DateRange attendantTime
                = DateRange.of(request.getAttendanceCheckStartedAt(), request.getAttendanceCheckEndedAt());

        final DateRange lateTime = DateRange.of(request.getAttendanceCheckEndedAt(), request.getLatenessCheckEndedAt());

        scheduleService.updateAttendanceTime(event, attendantTime, lateTime);

        final String attendanceCode = event.getAttendanceCode().getCode();
        final String qrCodeUrl = QrGenerator.generate(attendanceCode);

        return QrCodeResponse.of(qrCodeUrl, attendanceCode);
    }

    private void doUpdateSchedule(Schedule schedule, ScheduleCreateRequest request) {

        final List<EventCreateRequest> eventsCreateRequests
                = request.getEventsCreateRequests();

        for (EventCreateRequest eventCreateRequest : eventsCreateRequests) {

            final EventCreateDto eventCreateDto
                    = eventCreateRequest.toEventCreateDto();

            final Event event = scheduleService.addEvents(schedule, eventCreateDto);

            final List<ContentsCreateRequest> contentsCreateRequests
                    = eventCreateRequest.getContentsCreateRequests();

            for (ContentsCreateRequest contentsCreateRequest : contentsCreateRequests) {

                final ContentsCreateDto contentsCreateDto
                        = contentsCreateRequest.toDto();

                scheduleService.addContent(event, contentsCreateDto);
            }
        }
    }


}
