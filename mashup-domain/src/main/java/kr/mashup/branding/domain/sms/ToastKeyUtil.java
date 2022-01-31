package kr.mashup.branding.domain.sms;

import kr.mashup.branding.util.ProfileUtil;

public class ToastKeyUtil {

    public static String makeKey(SmsRequest smsRequest) {
        return new StringBuilder()
                .append(ProfileUtil.getProfile())
                .append("-")
                .append(smsRequest.getSmsRequestId())
                .toString();
    }

}
