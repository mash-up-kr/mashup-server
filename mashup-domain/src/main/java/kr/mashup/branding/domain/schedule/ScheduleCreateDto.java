package kr.mashup.branding.domain.schedule;

import java.time.LocalDateTime;

import kr.mashup.branding.util.DateRange;
import lombok.Value;

@Value(staticConstructor = "of")
public class ScheduleCreateDto {
    String name;
    DateRange dateRange;
}
