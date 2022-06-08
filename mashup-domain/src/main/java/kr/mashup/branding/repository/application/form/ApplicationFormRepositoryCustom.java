package kr.mashup.branding.repository.application.form;

import kr.mashup.branding.domain.application.form.ApplicationForm;
import kr.mashup.branding.domain.application.form.ApplicationFormQueryVo;
import org.springframework.data.domain.Page;

public interface ApplicationFormRepositoryCustom {
    Page<ApplicationForm> findByApplicationFormQueryVo(ApplicationFormQueryVo applicationFormQueryVo);
}
