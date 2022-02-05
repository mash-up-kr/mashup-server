package kr.mashup.branding.facade.application;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.mashup.branding.domain.application.Application;
import kr.mashup.branding.domain.application.ApplicationService;
import kr.mashup.branding.domain.application.CreateApplicationVo;
import kr.mashup.branding.domain.application.UpdateApplicationVo;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApplicationFacadeServiceImpl implements ApplicationFacadeService {
    private final ApplicationService applicationService;

    /**
     * 각 팀의 지원서 상세페이지 접근시 빈 지원서 생성
     */
    @Override
    public Application create(Long applicantId, CreateApplicationVo createApplicationVo) {
        return applicationService.create(applicantId, createApplicationVo);
    }

    /**
     * 지원서 임시저장
     */
    @Override
    public Application update(Long applicantId, Long applicationId, UpdateApplicationVo updateApplicationVo) {
        return applicationService.update(applicationId, updateApplicationVo);
    }

    /**
     * 지원서 제출
     */
    @Override
    public Application submit(Long applicantId, Long applicationId) {
        return applicationService.submit(applicationId);
    }

    /**
     * 내 지원서 목록 보기
     */
    @Override
    public List<Application> getApplications(Long applicantId) {
        return applicationService.getApplications(applicantId);
    }

    /**
     * 내 지원서 상세 보기
     */
    @Override
    public Application getApplication(Long applicantId, Long applicationId) {
        return applicationService.getApplication(applicantId, applicationId);
    }
}
