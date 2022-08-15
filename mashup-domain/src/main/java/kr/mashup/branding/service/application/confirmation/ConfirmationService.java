package kr.mashup.branding.service.application.confirmation;

public interface ConfirmationService {
    void updateToBeDeterminedToNotApplicable();

    void updateInterviewConfirmWaitingToRejected();

    void updateFinalConfirmWaitingToRejected();
}
