package kr.mashup.branding.ui.birthday.response;

import lombok.Getter;
import lombok.Value;

@Getter
@Value(staticConstructor = "of")
public class BirthdayCardResponse {
    Long id;
    String senderMemberName;
    String senderMemberPlatform;
    String message;
    String imageUrl;
}
