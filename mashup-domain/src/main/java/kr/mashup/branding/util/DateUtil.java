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

    /**
     *
     * is base date range contains target date range
     * ex base - start: 1월1일00시, end 1월 2일 00시
     *    target - start : 1월1일00시, end 1월1일23시 : true
     */
    public static boolean isContainDateRange(LocalDateTime baseStartedAt,
                                        LocalDateTime baseEndedAt,
                                        LocalDateTime targetStartedAt,
                                        LocalDateTime targetEndedAt){

        boolean isBaseStartBeforeOrEqualTargetStart
                = baseStartedAt.isBefore(targetStartedAt) || baseStartedAt.isEqual(targetStartedAt);

        boolean isBaseEndBeforeOrEqualTargetEnd
                = baseEndedAt.isAfter(targetEndedAt) || baseEndedAt.isEqual(targetEndedAt);

        return isBaseStartBeforeOrEqualTargetStart && isBaseEndBeforeOrEqualTargetEnd;
    }

    public static boolean isContainDateRange(LocalDate baseStartedAt,
                                             LocalDate baseEndedAt,
                                             LocalDate targetStartedAt,
                                             LocalDate targetEndedAt){

        boolean isBaseStartBeforeOrEqualTargetStart
                = baseStartedAt.isBefore(targetStartedAt) || baseStartedAt.isEqual(targetStartedAt);

        boolean isBaseEndBeforeOrEqualTargetEnd
                = baseEndedAt.isAfter(targetEndedAt) || baseEndedAt.isEqual(targetEndedAt);

        return isBaseStartBeforeOrEqualTargetStart && isBaseEndBeforeOrEqualTargetEnd;
    }


}
