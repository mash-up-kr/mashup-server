package kr.mashup.branding.util;

public class ProfileUtil {
    public static String getProfile() {
        return  System.getProperty("spring.profiles.active");
    }
}
