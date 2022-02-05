package kr.mashup.branding.ui.application.progress;

import org.springframework.stereotype.Component;

import kr.mashup.branding.domain.application.progress.ApplicationProgress;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ApplicationProgressAssembler {

    ApplicationProgressResponse toApplicationProgressResponse(Long applicationId,
        ApplicationProgress applicationProgress) {
        return new ApplicationProgressResponse(applicationId, applicationProgress.getStatus());
    }
}
