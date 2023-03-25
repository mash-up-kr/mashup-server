package kr.mashup.branding.ui.schedule.response;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.Value;

@Getter
@Value(staticConstructor = "of")
public class ScheduleResponseList {

    Progress progress;

    Integer dateCount;

    List<ScheduleResponse> scheduleList;

    public static ScheduleResponseList of(Progress progress, Optional<Integer> dateCount, List<ScheduleResponse> scheduleList){
        return ScheduleResponseList.of(progress, dateCount.orElse(null), scheduleList);
    }
}
