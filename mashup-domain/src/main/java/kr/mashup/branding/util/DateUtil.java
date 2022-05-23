package kr.mashup.branding.util;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DateUtil {

    public static boolean checkStartBeforeEnd(LocalDate startedAt, LocalDate ended_at){
        return !startedAt.isAfter(ended_at) && !startedAt.isEqual(ended_at);
    }

    public static boolean checkStartBeforeEnd(LocalDateTime startedAt, LocalDateTime ended_at){
        return !startedAt.isAfter(ended_at) && !startedAt.isEqual(ended_at);
    }
}
