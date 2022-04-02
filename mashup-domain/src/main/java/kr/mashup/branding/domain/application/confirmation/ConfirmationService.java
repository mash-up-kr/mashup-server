package kr.mashup.branding.domain.application.confirmation;

public interface ConfirmationService {
    void updateToBeDeterminedToNotApplicable();

    void updateInterviewConfirmWaitingToRejected();

    void updateFinalConfirmWaitingToRejected();
}
