package kr.mashup.branding.ui.notification.sms;

import org.springframework.stereotype.Component;

import kr.mashup.branding.domain.application.ApplicationService;
import kr.mashup.branding.domain.application.ApplicationStatus;
import kr.mashup.branding.domain.notification.sms.SmsRequest;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SmsRequestAssembler {
    private final ApplicationService applicationService;

    public SmsRequestSimpleResponse toSmsRequestResponse(SmsRequest smsRequest) {
        return new SmsRequestSimpleResponse(
            smsRequest.getSmsRequestId(),
            smsRequest.getStatus(),
            smsRequest.getRecipientApplicant().getName(),
            smsRequest.getRecipientPhoneNumber(),
            getTeamName(smsRequest.getRecipientApplicant().getApplicantId())
        );
    }

    public SmsRequestDetailResponse toSmsRequestDetailResponse(SmsRequest smsRequest) {
        return new SmsRequestDetailResponse(
            smsRequest.getSmsRequestId(),
            smsRequest.getNotification().getName(),
            smsRequest.getNotification().getContent(),
            smsRequest.getStatus(),
            smsRequest.getRecipientApplicant().getName(),
            smsRequest.getRecipientPhoneNumber(),
            getTeamName(smsRequest.getRecipientApplicant().getApplicantId())
        );
    }

    private String getTeamName(Long applicantId) {
        return applicationService.getApplications(applicantId)
            .stream()
            .filter(it -> it.getStatus() != ApplicationStatus.CREATED)
            .findFirst()
            .map(it -> it.getApplicationForm().getTeam().getName())
            .orElse(null);
    }
}
