package kr.mashup.branding.ui.member.request;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class PushNotificationRequest {

    @NotNull(message = "푸시 알림 동의 여부는 필수값입니다.")
    private Boolean pushNotificationAgreed;
}
