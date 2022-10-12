package kr.mashup.branding.facade.application.form;

import kr.mashup.branding.domain.team.Team;
import kr.mashup.branding.service.team.TeamService;
import kr.mashup.branding.ui.application.form.vo.ApplicationFormResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import kr.mashup.branding.service.application.ApplicationService;
import kr.mashup.branding.domain.application.form.ApplicationForm;
import kr.mashup.branding.domain.application.form.ApplicationFormQueryVo;
import kr.mashup.branding.service.application.ApplicationFormService;
import kr.mashup.branding.domain.application.form.CreateApplicationFormVo;
import kr.mashup.branding.domain.application.form.UpdateApplicationFormVo;
import kr.mashup.branding.facade.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminApplicationFormFacadeService {
    private final TeamService teamService;
    private final ApplicationFormService applicationFormService;
    private final ApplicationService applicationService;
    private final ProfileService profileService;

    public ApplicationFormResponse create(Long adminMemberId,Long teamId, CreateApplicationFormVo createApplicationFormVo) {
        Team team = teamService.getTeam(teamId);
        ApplicationForm applicationForm = applicationFormService.create(adminMemberId, team, createApplicationFormVo);

        return ApplicationFormResponse.from(applicationForm);
    }

    public ApplicationFormResponse update(
        Long adminMemberId,
        Long applicationFormId,
        UpdateApplicationFormVo updateApplicationFormVo
    ) {
        ApplicationForm applicationForm = applicationFormService.update(adminMemberId, applicationFormId, updateApplicationFormVo);
        return ApplicationFormResponse.from(applicationForm);
    }

    public Page<ApplicationFormResponse> getApplicationForms(ApplicationFormQueryVo applicationFormQueryVo) {
        return applicationFormService
            .getApplicationForms(applicationFormQueryVo)
            .map(ApplicationFormResponse::from);
    }

    public ApplicationFormResponse getApplicationForm(Long applicationFormId) {
        ApplicationForm applicationForm = applicationFormService.getApplicationFormById(applicationFormId);
        return ApplicationFormResponse.from(applicationForm);
    }

    public void delete(Long adminMemberId, Long applicationFormId) {
        // 개발 환경에서는 설문지 삭제 시도시 지원서를 모두 삭제 후 설문지까지 삭제 한다.
        if (profileService.isLocal() || profileService.isDevelop()) {
            applicationService.deleteByApplicationFormId(applicationFormId);
        }
        applicationFormService.delete(adminMemberId, applicationFormId);
    }
}
