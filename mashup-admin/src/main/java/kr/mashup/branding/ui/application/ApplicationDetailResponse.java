package kr.mashup.branding.ui.application;

import java.time.LocalDateTime;
import java.util.List;

import kr.mashup.branding.domain.application.confirmation.ApplicantConfirmationStatus;
import kr.mashup.branding.ui.applicant.ApplicantResponse;
import kr.mashup.branding.ui.application.form.QuestionResponse;
import kr.mashup.branding.ui.notification.sms.SmsRequestDetailResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApplicationDetailResponse {
    private Long applicationId;
    private ApplicantResponse applicant;
    private List<QuestionResponse> questions;
    private List<AnswerResponse> answers;
    private ApplicantConfirmationStatus confirmationStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<SmsRequestDetailResponse> smsRequests;
}
