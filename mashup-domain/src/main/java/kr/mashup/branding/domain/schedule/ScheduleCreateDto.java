package kr.mashup.branding.domain.schedule;

import kr.mashup.branding.util.DateRange;
import lombok.Value;

@Value(staticConstructor = "of")
public class ScheduleCreateDto {
    String name;
    DateRange dateRange;
    Double latitude;
    Double longitude;
    String roadAddress;
    String detailAddress;
    ScheduleType scheduleType;
    String notice;
}
