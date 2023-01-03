package kr.mashup.branding.service.email;

public class EmailResponse {

    private final String messageId;
    private final EmailResponseStatus status;

    public static EmailResponse success(String messageId) {
        return new EmailResponse(messageId, EmailResponseStatus.SUCCESS);
    }

    public static EmailResponse fail() {
        return new EmailResponse("", EmailResponseStatus.FAIL);
    }

    public boolean isSuccess(){
        return this.status.equals(EmailResponseStatus.SUCCESS);
    }

    public String getMessageId() {
        return messageId;
    }

    public EmailResponseStatus getStatus() {
        return status;
    }

    private EmailResponse(String messageId, EmailResponseStatus status) {
        this.messageId = messageId;
        this.status = status;
    }
}
