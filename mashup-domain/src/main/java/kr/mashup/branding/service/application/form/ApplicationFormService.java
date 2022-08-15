package kr.mashup.branding.service.application.form;

import java.util.List;

import kr.mashup.branding.domain.application.form.ApplicationForm;
import kr.mashup.branding.domain.application.form.ApplicationFormQueryVo;
import kr.mashup.branding.domain.application.form.CreateApplicationFormVo;
import kr.mashup.branding.domain.application.form.UpdateApplicationFormVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ApplicationFormService {
    ApplicationForm create(Long adminMemberId, CreateApplicationFormVo createApplicationFormVo);

    ApplicationForm update(Long adminMemberId, Long applicationFormId,
        UpdateApplicationFormVo updateApplicationFormVo);

    void delete(Long adminMemberId, Long applicationFormId);

    ApplicationForm getApplicationFormById(Long applicationFormId);

    List<ApplicationForm> getApplicationFormsByTeamId(Long teamId);

    Page<ApplicationForm> getApplicationForms(ApplicationFormQueryVo applicationFormQueryVo);

    Page<ApplicationForm> getApplicationForms(Long teamId, Pageable pageable);
}
