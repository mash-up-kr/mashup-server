package kr.mashup.branding.infrastructure.sms;

public enum ToastSmsResponseStatus {
    SUCCESS("발송 완료", 0),
    // header.resultCode 에서는 현재 0만 사용 나머지는 확인 불가
    IN_PROGRESS("발송 중", 1),
    FAILURE("발송 실패", 2),
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
