package kr.mashup.branding.ui.schedule.response;

import java.util.List;

import lombok.Getter;
import lombok.Value;

@Getter
@Value(staticConstructor = "of")
public class ScheduleResponseListWithProgress {

    Progress progress;

    Integer dateCount;

    List<ScheduleResponse> scheduleList;
}
