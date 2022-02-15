package kr.mashup.branding.facade.application;

import java.util.List;

import kr.mashup.branding.domain.application.Application;
import kr.mashup.branding.domain.notification.sms.SmsRequest;
import lombok.Value;

@Value(staticConstructor = "of")
public class ApplicationDetailVo {
    Application application;
    List<SmsRequest> smsRequests;
}
