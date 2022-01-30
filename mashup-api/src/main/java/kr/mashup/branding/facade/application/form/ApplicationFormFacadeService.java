package kr.mashup.branding.facade.application.form;

import java.util.List;

import kr.mashup.branding.domain.application.form.ApplicationForm;

public interface ApplicationFormFacadeService {
    List<ApplicationForm> getByTeamId(Long teamId);
}
