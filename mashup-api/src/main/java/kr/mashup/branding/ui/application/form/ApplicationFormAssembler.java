package kr.mashup.branding.ui.application.form;

import org.springframework.stereotype.Component;

import kr.mashup.branding.domain.application.form.ApplicationForm;

@Component
public class ApplicationFormAssembler {
    ApplicationFormResponse toApplicationFormResponse(ApplicationForm applicationForm) {
        return new ApplicationFormResponse(
            applicationForm.getApplicationFormId()
        );
    }
}
