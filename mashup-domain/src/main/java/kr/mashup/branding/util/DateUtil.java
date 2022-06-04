package kr.mashup.branding.util;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DateUtil {

    public static boolean isStartBeforeOrEqualEnd(LocalDate startedAt, LocalDate ended_at){
        return startedAt.isBefore(ended_at) || startedAt.isEqual(ended_at);
    }

    public static boolean isStartBeforeOrEqualEnd(LocalDateTime startedAt, LocalDateTime ended_at){
        return startedAt.isBefore(ended_at) || startedAt.isEqual(ended_at);
    }


    public static boolean isContainDateRange(
                                        DateRange baseRange,
                                        DateRange targetRange){

        LocalDateTime baseStartedAt = baseRange.getStart();
        LocalDateTime baseEndedAt = baseRange.getEnd();

        LocalDateTime targetStartedAt = targetRange.getStart();
        LocalDateTime targetEndedAt = targetRange.getEnd();


        boolean isBaseStartBeforeOrEqualTargetStart
                = baseStartedAt.isBefore(targetStartedAt) || baseStartedAt.isEqual(targetStartedAt);

        boolean isBaseEndAfterOrEqualTargetEnd
                = baseEndedAt.isAfter(targetEndedAt) || baseEndedAt.isEqual(targetEndedAt);

        return isBaseStartBeforeOrEqualTargetStart && isBaseEndAfterOrEqualTargetEnd;
    }


}
