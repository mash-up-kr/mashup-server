package kr.mashup.branding.domain.member;

import com.fasterxml.jackson.annotation.JsonCreator;
import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.exception.BadRequestException;
import kr.mashup.branding.domain.schedule.ScheduleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Getter
public enum Platform {
    SPRING("Spring"),
    IOS("iOS"),
    ANDROID("Android"),
    DESIGN("Product Design"),
    WEB("Web"),
    NODE("Node");

    private final String name;

    @JsonCreator
    public static Platform from(String s) {
        try {
            return Platform.valueOf(s.toUpperCase());
        } catch (Exception e) {
            log.info("platform type conversion error : {}", e.getMessage());
            throw new BadRequestException(ResultCode.INVALID_PLATFORM_NAME);
        }
    }

    public Boolean checkAvailabilityByScheduleType(ScheduleType scheduleType) {
        if (scheduleType == ScheduleType.ALL) return true;
        if (scheduleType == ScheduleType.SPRING && this == SPRING) return true;
        if (scheduleType == ScheduleType.IOS && this == IOS) return true;
        if (scheduleType == ScheduleType.DESIGN && this == DESIGN) return true;
        if (scheduleType == ScheduleType.WEB && this == WEB) return true;
        if (scheduleType == ScheduleType.NODE && this == NODE) return true;
        return false;
    }
}
