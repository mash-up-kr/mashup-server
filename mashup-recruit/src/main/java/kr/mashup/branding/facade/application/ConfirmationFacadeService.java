package kr.mashup.branding.facade.application;

public interface ConfirmationFacadeService {
    void updateToBeDeterminedToNotApplicable();

    void updateInterviewConfirmWaitingToRejected();

    void updateFinalConfirmWaitingToRejected();
}
