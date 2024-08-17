package kr.mashup.branding.ui.birthday.response;

import lombok.Getter;
import lombok.Value;

@Getter
@Value(staticConstructor = "of")
public class BirthdayCardDefaultImageResponse {
    String imageUrl;
}
