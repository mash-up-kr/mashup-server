package kr.mashup.branding.service.email;

import lombok.Value;

@Value(staticConstructor = "of")
public class EmailNotificationEvent {
    Long emailNotificationId;
}
