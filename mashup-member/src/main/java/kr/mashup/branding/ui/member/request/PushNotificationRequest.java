package kr.mashup.branding.ui.member.request;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class PushNotificationRequest {

    @NotNull(message = "매시업 소식 알림 동의 여부는 필수값입니다.")
    private Boolean newsPushNotificationAgreed;

    @NotNull(message = "당근ㄴ 흔들기 알림 동의 여부는 필수값입니다.")
    private Boolean danggnPushNotificationAgreed;
}
