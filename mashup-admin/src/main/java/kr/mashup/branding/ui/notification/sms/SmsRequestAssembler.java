package kr.mashup.branding.ui.notification.sms;

import org.springframework.stereotype.Component;

import kr.mashup.branding.domain.application.ApplicationService;
import kr.mashup.branding.domain.application.ApplicationStatus;
import kr.mashup.branding.domain.notification.sms.SmsRequest;
import kr.mashup.branding.domain.team.Team;
import kr.mashup.branding.ui.team.TeamAssembler;
import kr.mashup.branding.ui.team.TeamResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SmsRequestAssembler {
    private final ApplicationService applicationService;
    private final TeamAssembler teamAssembler;

    public SmsRequestSimpleResponse toSmsRequestResponse(SmsRequest smsRequest) {
        return new SmsRequestSimpleResponse(
            smsRequest.getSmsRequestId(),
            smsRequest.getStatus(),
            smsRequest.getRecipientApplicant().getName(),
            smsRequest.getRecipientPhoneNumber(),
            getTeamResponse(smsRequest.getRecipientApplicant().getApplicantId())
        );
    }

    public SmsRequestDetailResponse toSmsRequestDetailResponse(SmsRequest smsRequest, Team team) {
        return new SmsRequestDetailResponse(
            smsRequest.getSmsRequestId(),
            smsRequest.getNotification().getName(),
            smsRequest.getNotification().getContent(),
            smsRequest.getStatus(),
            teamAssembler.toTeamResponse(team)
        );
    }

    private TeamResponse getTeamResponse(Long applicantId) {
        return applicationService.getApplications(applicantId)
            .stream()
            .filter(it -> it.getStatus() != ApplicationStatus.CREATED)
            .findFirst()
            .map(it -> it.getApplicationForm().getTeam())
            .map(teamAssembler::toTeamResponse)
            .orElse(null);
    }
}
