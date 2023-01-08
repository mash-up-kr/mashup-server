package kr.mashup.branding.ui.application.vo;

import kr.mashup.branding.domain.application.Application;
import kr.mashup.branding.domain.application.confirmation.ApplicantConfirmationStatus;
import kr.mashup.branding.ui.applicant.ApplicantResponse;
import kr.mashup.branding.ui.team.vo.TeamResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ApplicationSimpleResponse {
    private Long applicationId;
    private ApplicantResponse applicant;
    private TeamResponse team;
    private ApplicantConfirmationStatus confirmationStatus;
    private String rejectionReason;
    private ApplicationResultResponse result;
    private LocalDateTime submittedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ApplicationSimpleResponse from(Application application){
        return new ApplicationSimpleResponse(
            application.getApplicationId(),
            ApplicantResponse.from(application.getApplicant()),
            TeamResponse.from(application.getApplicationForm().getTeam()),
            application.getConfirmation().getStatus(),
            application.getConfirmation().getRejectionReason(),
                ApplicationResultResponse.from(application.getApplicationResult()),
            application.getSubmittedAt(),
            application.getCreatedAt(),
            application.getUpdatedAt()
        );
    }
}
