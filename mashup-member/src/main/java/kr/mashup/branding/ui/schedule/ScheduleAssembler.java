package kr.mashup.branding.ui.schedule;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import kr.mashup.branding.domain.schedule.Schedule;
import kr.mashup.branding.ui.event.EventAssembler;
import kr.mashup.branding.ui.schedule.response.ScheduleResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ScheduleAssembler {
    private final EventAssembler eventAssembler;

    public ScheduleResponse toScheduleResponse(Schedule schedule) {
        return new ScheduleResponse(
            schedule.getId(),
            schedule.getName(),
            schedule.getStartedAt(),
            schedule.getEndedAt(),
            schedule.getGeneration().getNumber(),
            schedule.getEventList()
                .stream()
                .map(eventAssembler::toEventResponse)
                .collect(Collectors.toList())
        );
    }
}
