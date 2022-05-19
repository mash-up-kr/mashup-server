package kr.mashup.branding.facade.application;

import java.util.List;

import org.springframework.data.domain.Page;

import kr.mashup.branding.domain.application.Application;
import kr.mashup.branding.domain.application.ApplicationQueryVo;
import kr.mashup.branding.domain.application.result.UpdateApplicationResultVo;

public interface ApplicationFacadeService {
    Page<Application> getApplications(Long adminMemberId, ApplicationQueryVo applicationQueryVo);

    ApplicationDetailVo getApplicationDetail(Long adminMemberId, Long applicationId);

    List<Application> updateResults(Long adminMemberId, List<UpdateApplicationResultVo> updateApplicationResultVoList);

    Application updateResult(Long adminMemberId, UpdateApplicationResultVo updateApplicationResultVo);
}
