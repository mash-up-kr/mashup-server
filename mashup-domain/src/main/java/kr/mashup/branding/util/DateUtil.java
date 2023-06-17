package kr.mashup.branding.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

public class DateUtil {

    public static boolean isStartBeforeOrEqualEnd(
        LocalDateTime startedAt,
        LocalDateTime endedAt
    ) {
        return startedAt.isBefore(endedAt) || startedAt.isEqual(endedAt);
    }

    public static boolean isStartBeforeOrEqualEnd(
        LocalDate startedAt,
        LocalDate endedAt
    ) {
        return startedAt.isBefore(endedAt) || startedAt.isEqual(endedAt);
    }

    public static boolean isInTime(
        LocalDateTime start,
        LocalDateTime end,
        LocalDateTime target) {
        return !(target.isBefore(start) || target.isAfter(end));
    }

    public static boolean isInTime(
            LocalDate start,
            LocalDate end,
            LocalDate target) {
        return !(target.isBefore(start) || target.isAfter(end));
    }

    public static boolean isContainDateRange(
        DateRange baseRange,
        DateRange targetRange
    ) {

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

    public static LocalDateTime fromEpoch(Long epochSecond){
        return LocalDateTime.ofEpochSecond(epochSecond, 0, ZoneOffset.of("+9"));
    }

    public static LocalDateTime fromEpochString(String epochSecond){
        return fromEpoch(Long.parseLong(epochSecond));
    }

    public static Integer countDayFromNow(LocalDateTime startedAt, LocalDateTime currentTime) {
        return (int) ChronoUnit.DAYS.between(
            currentTime.truncatedTo(ChronoUnit.DAYS),
            startedAt.truncatedTo(ChronoUnit.DAYS)
        );
    }
}
