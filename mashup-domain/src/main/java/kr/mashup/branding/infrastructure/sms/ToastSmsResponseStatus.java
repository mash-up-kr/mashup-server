package kr.mashup.branding.infrastructure.sms;

public enum ToastSmsResponseStatus {
    // 요청 상태 코드(1:요청 중, 2:요청 완료, 3:요청 실패) -> body.data.statusCode
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
