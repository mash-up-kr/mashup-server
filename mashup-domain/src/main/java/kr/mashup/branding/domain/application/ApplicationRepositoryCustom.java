package kr.mashup.branding.domain.application;

import org.springframework.data.domain.Page;

public interface ApplicationRepositoryCustom {
    Page<Application> findBy(ApplicationQueryVo applicationQueryVo);
}
