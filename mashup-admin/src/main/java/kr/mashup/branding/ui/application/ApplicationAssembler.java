package kr.mashup.branding.ui.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import kr.mashup.branding.domain.application.Application;
import kr.mashup.branding.domain.application.result.ApplicationResultStatus;
import kr.mashup.branding.domain.application.result.UpdateApplicationResultVo;
import kr.mashup.branding.domain.application.result.UpdateApplicationResultsVo;

@Component
public class ApplicationAssembler {
    ApplicationResponse toApplicationResponse(Application application) {
        return new ApplicationResponse(
            application.getApplicationId()
        );
    }

    public List<UpdateApplicationResultsVo> toUpdateApplicationResultsVoList(UpdateApplicationResultsRequest request) {
        ApplicationResultStatus status = ApplicationResultStatus.valueOf(request.getApplicationResultStatus());
        return request.getApplicationIds()
            .stream()
            .map(it -> UpdateApplicationResultsVo.of(it, status))
            .collect(Collectors.toList());
    }

    UpdateApplicationResultVo toUpdateApplicationResultVo(UpdateApplicationResultRequest request) {
        ApplicationResultStatus status = ApplicationResultStatus.valueOf(request.getApplicationResultStatus());
        return UpdateApplicationResultVo.of(status, request.getInterviewStartedAt(), request.getInterviewEndedAt());
    }
}
