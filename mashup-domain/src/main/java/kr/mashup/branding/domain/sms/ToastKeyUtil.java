package kr.mashup.branding.domain.sms;

import kr.mashup.branding.util.ProfileUtil;

public class ToastKeyUtil {

    public static String makeKey(Long requestGroupId, Long userId) {
        return new StringBuilder()
                .append(ProfileUtil.getProfile())
                .append("-")
                .append(requestGroupId.toString())
                .append("-")
                .append(userId.toString())
                .toString();
    }

}
