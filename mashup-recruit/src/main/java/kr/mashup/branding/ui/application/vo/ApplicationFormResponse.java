package kr.mashup.branding.ui.application.vo;

import kr.mashup.branding.domain.application.form.ApplicationForm;
import lombok.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApplicationFormResponse {

    private Long teamId;
    private Long applicationFormId;

    public static ApplicationFormResponse of(final ApplicationForm applicationForm){
        return new ApplicationFormResponse(applicationForm.getTeam().getTeamId(), applicationForm.getApplicationFormId());
    }

}
