package kr.mashup.branding.infrastructure.sms;

public enum ToastSmsResponseStatus {
    IN_PROGRESS("발송 중", 1),
    SUCCESS("발송 완료", 2),
    FAILURE("발송 실패", 3),
    ;

    private final int toastSmsStatusCode;

    ToastSmsResponseStatus(String description, int toastSmsStatusCode) {
        this.toastSmsStatusCode = toastSmsStatusCode;
    }

    public static boolean isInProgress(Integer toastSmsStatusCode) {
        return toastSmsStatusCode != null && toastSmsStatusCode.equals(IN_PROGRESS.toastSmsStatusCode);
    }

    public static boolean isSuccess(Integer toastSmsStatusCode) {
        return toastSmsStatusCode != null && toastSmsStatusCode.equals(SUCCESS.toastSmsStatusCode);
    }
}
