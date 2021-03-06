package kr.mashup.branding.repository.application;

import kr.mashup.branding.domain.application.Application;
import kr.mashup.branding.domain.application.ApplicationQueryVo;
import org.springframework.data.domain.Page;

public interface ApplicationRepositoryCustom {
    Page<Application> findBy(ApplicationQueryVo applicationQueryVo);
}
