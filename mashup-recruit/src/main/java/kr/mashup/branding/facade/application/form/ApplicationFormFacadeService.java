package kr.mashup.branding.facade.application.form;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.mashup.branding.domain.application.form.ApplicationForm;
import kr.mashup.branding.service.application.ApplicationFormService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApplicationFormFacadeService {
    private final ApplicationFormService applicationFormService;

    public List<ApplicationForm> getByTeamId(Long teamId) {
        return applicationFormService.getApplicationFormsByTeamId(teamId);
    }
}
