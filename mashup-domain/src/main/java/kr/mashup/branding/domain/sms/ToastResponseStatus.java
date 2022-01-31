package kr.mashup.branding.domain.sms;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ToastResponseStatus {
    IN_PROGRESS(1), COMPLETE(2), FAIL(3);

    private final Integer code;
}
