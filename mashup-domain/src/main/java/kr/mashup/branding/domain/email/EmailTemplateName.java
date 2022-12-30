package kr.mashup.branding.domain.email;

import com.fasterxml.jackson.annotation.JsonCreator;
import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum EmailTemplateName {

    TEST("test"),
    SUBMIT("submit");

    EmailTemplateName(String registeredTemplateName) {
        this.registeredTemplateName = registeredTemplateName;
    }

    // ses 에 등록된 템플릿 이름
    private String registeredTemplateName;

    @JsonCreator
    public static EmailTemplateName from(String s) {
        try {
            return EmailTemplateName.valueOf(s.toUpperCase());
        } catch (Exception e) {
            log.info("platform type conversion error : {}", e.getMessage());
            throw new BadRequestException(ResultCode.INVALID_PLATFORM_NAME);
        }
    }
}
