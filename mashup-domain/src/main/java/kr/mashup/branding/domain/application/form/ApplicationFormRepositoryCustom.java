package kr.mashup.branding.domain.application.form;

import org.springframework.data.domain.Page;

public interface ApplicationFormRepositoryCustom {
    Page<ApplicationForm> findByApplicationFormQueryVo(ApplicationFormQueryVo applicationFormQueryVo);
}
