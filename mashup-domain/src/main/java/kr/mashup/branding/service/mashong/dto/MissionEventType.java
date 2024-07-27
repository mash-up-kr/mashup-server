package kr.mashup.branding.service.mashong.dto;

public enum MissionEventType {

    APPLY,
    SET_TO_VALUE,
    ;

    public boolean isApplyType() {
        return this.equals(APPLY);
    }
}
