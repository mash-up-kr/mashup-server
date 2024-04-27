package kr.mashup.branding.util;


public class QrGenerator {

    private static final String QR_URL = "https://quickchart.io/chart";
    private static final String QR_TYPE = "qr";
    private static final String QR_SIZE = "300x300";

    public static String generate(String data) {
        return QR_URL + "?cht=" + QR_TYPE + "&chs=" + QR_SIZE + "&chl=" + data;
    }

}
