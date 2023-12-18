package kr.mashup.branding.ui.attendance.request;

import lombok.Getter;

@Getter
public class AttendanceCheckRequest {
    private String checkingCode;
    private Double latitude;
    private Double longitude;
}
