package kr.mashup.branding.ui.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import kr.mashup.branding.domain.application.Application;
import kr.mashup.branding.domain.application.result.ApplicationResultStatus;
import kr.mashup.branding.domain.application.result.UpdateApplicationResultVo;

@Component
public class ApplicationAssembler {
    ApplicationResponse toApplicationResponse(Application application) {
        return new ApplicationResponse(
            application.getApplicationId()
        );
    }

    public List<UpdateApplicationResultVo> toUpdateApplicationResultVoList(UpdateApplicationResultRequest request) {
        ApplicationResultStatus status = ApplicationResultStatus.valueOf(request.getApplicationResultStatus());
        return request.getApplicationIds()
            .stream()
            .map(it -> UpdateApplicationResultVo.of(it, status))
            .collect(Collectors.toList());
    }
}
