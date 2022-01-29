package kr.mashup.branding.domain.sms.exception;

public class SmsSendFailException extends Exception{
    SmsSendFailException(String message) {
        super(message);
    }
}
