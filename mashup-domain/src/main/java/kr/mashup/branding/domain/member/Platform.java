package kr.mashup.branding.domain.member;

import com.fasterxml.jackson.annotation.JsonCreator;
import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum Platform {

    SPRING, IOS, ANDROID, DESIGN, WEB, NODE;

    @JsonCreator
    public static Platform from(String s){
        try{
            return Platform.valueOf(s);
        }catch (Exception e){
            log.info("platform type conversion error : {}", e.getMessage());
            throw new BadRequestException(ResultCode.INVALID_PLATFORM_NAME);
        }
    }
}
