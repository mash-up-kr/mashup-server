package kr.mashup.branding.facade.schedule;

import kr.mashup.branding.domain.attendance.AttendanceCode;
import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.pushnoti.vo.SeminarUpdatedVo;
import kr.mashup.branding.domain.schedule.ContentsCreateDto;
import kr.mashup.branding.domain.schedule.Event;
import kr.mashup.branding.domain.schedule.Schedule;
import kr.mashup.branding.domain.schedule.ScheduleCreateDto;
import kr.mashup.branding.infrastructure.pushnoti.PushNotiEventPublisher;
import kr.mashup.branding.service.generation.GenerationService;
import kr.mashup.branding.service.member.MemberService;
import kr.mashup.branding.service.schedule.EventCreateDto;
import kr.mashup.branding.service.schedule.ScheduleService;
import kr.mashup.branding.ui.schedule.response.QrCodeResponse;
import kr.mashup.branding.ui.schedule.request.ContentsCreateRequest;
import kr.mashup.branding.ui.schedule.request.EventCreateRequest;
import kr.mashup.branding.ui.schedule.request.QrCodeGenerateRequest;
import kr.mashup.branding.ui.schedule.request.ScheduleUpdateRequest;
import kr.mashup.branding.ui.schedule.response.ScheduleResponse;
import kr.mashup.branding.util.DateRange;
import kr.mashup.branding.util.QrGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ScheduleFacadeService {
    private final ScheduleService scheduleService;
    private final GenerationService generationService;
    private final PushNotiEventPublisher pushNotiEventPublisher;
    private final MemberService memberService;

    public Page<ScheduleResponse> getSchedules(Integer generationNumber, Pageable pageable) {

        final Generation generation
                = generationService.getByNumberOrThrow(generationNumber);

        return scheduleService
                .getByGeneration(generation, pageable)
                .map(ScheduleResponse::from);
    }

    @Transactional
    public ScheduleResponse create(Integer generationNumber, ScheduleUpdateRequest request) {
        final Generation generation =
                generationService.getByNumberOrThrow(generationNumber);
        final DateRange dateRange
                = DateRange.of(request.getStartedAt(), request.getEndedAt());
        final Schedule schedule
                = scheduleService.create(generation, ScheduleCreateDto.of(request.getName(), dateRange));

        doUpdateSchedule(schedule, request);

        return ScheduleResponse.from(schedule);
    }

    @Transactional
    public void publishSchedule(Long scheduleId) {

        final Schedule schedule
                = scheduleService.getByIdOrThrow(scheduleId);

        scheduleService.publishSchedule(schedule);

        pushNotiEventPublisher.publishPushNotiSendEvent(
            new SeminarUpdatedVo(memberService.getAllPushNotiTargetableMembers())
        );
    }

    @Transactional
    public void hideSchedule(Long scheduleId) {
        final Schedule schedule
                = scheduleService.getByIdOrThrow(scheduleId);
        scheduleService.hideSchedule(schedule);
    }

    @Transactional
    public ScheduleResponse updateSchedule(Long scheduleId, ScheduleUpdateRequest request) {
        final Schedule schedule
                = scheduleService.getByIdOrThrow(scheduleId);

        doUpdateSchedule(schedule, request);
        return ScheduleResponse.from(schedule);
    }

    @Transactional
    public void deleteSchedule(Long scheduleId) {
        final Schedule schedule
                = scheduleService.getByIdOrThrow(scheduleId);

        scheduleService.deleteSchedule(schedule);
    }

    @Transactional
    public QrCodeResponse generateQrCode(Long scheduleId, Long eventId, QrCodeGenerateRequest request) {

        final Schedule schedule
                = scheduleService.getByIdOrThrow(scheduleId);

        final Event event
                = scheduleService.getEventOrThrow(schedule, eventId);

        final DateRange codeValidRequestTime
                = DateRange.of(request.getStartedAt(), request.getEndedAt());

        final AttendanceCode attendanceCode
                = scheduleService.addAttendanceCode(event, codeValidRequestTime);

        final String qrCodeUrl = QrGenerator.generate(attendanceCode.getCode());

        return QrCodeResponse.of(qrCodeUrl);
    }

    private void doUpdateSchedule(Schedule schedule, ScheduleUpdateRequest request) {

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
