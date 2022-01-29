package kr.mashup.branding.facade.application;

import kr.mashup.branding.domain.application.Application;
import kr.mashup.branding.domain.application.ApplicationService;
import kr.mashup.branding.domain.application.CreateApplicationVo;
import kr.mashup.branding.domain.application.UpdateApplicationVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationFacadeServiceImpl implements ApplicationFacadeService {
    private final ApplicationService applicationService;

    /**
     * 각 팀의 지원서 상세페이지 접근시 빈 지원서 생성
     */
    @Override
    public Application create(CreateApplicationVo createApplicationVo) {
        return applicationService.createApplication(createApplicationVo);
    }

    /**
     * 지원서 임시저장
     */
    @Override
    public Application update(UpdateApplicationVo updateApplicationVo) {
        return applicationService.updateApplication(updateApplicationVo);
    }

    /**
     * 지원서 제출
     */
    @Override
    public Application submit() {
        return null;
    }

    @Override
    public Application getApplications() {
        return null;
    }

    @Override
    public Application getApplication(Long applicationId) {
        return null;
    }
}
