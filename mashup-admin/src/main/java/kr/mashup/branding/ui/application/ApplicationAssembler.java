package kr.mashup.branding.ui.application;

import org.springframework.stereotype.Component;

import kr.mashup.branding.domain.application.Application;

@Component
public class ApplicationAssembler {
    ApplicationResponse toApplicationResponse(Application application) {
        return new ApplicationResponse(
            application.getApplicationId()
        );
    }
}
