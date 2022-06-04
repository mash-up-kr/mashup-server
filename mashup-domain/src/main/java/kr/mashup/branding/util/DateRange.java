package kr.mashup.branding.util;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
public class DateRange {

    LocalDateTime start;
    LocalDateTime end;

    public static DateRange of(LocalDateTime start, LocalDateTime end){
        return new DateRange(start, end);
    }

    public static DateRange of(LocalDate start, LocalDate end){
        return new DateRange(LocalDateTime.of(start, LocalTime.MIN), LocalDateTime.of(end, LocalTime.MAX));
    }

    private DateRange(LocalDateTime start, LocalDateTime end){
        if(!DateUtil.isStartBeforeOrEqualEnd(start, end)){
            throw new IllegalArgumentException("유효하지 않은 날짜 범위입니다.");
        }
        this.start = start;
        this.end = end;
    }

}
