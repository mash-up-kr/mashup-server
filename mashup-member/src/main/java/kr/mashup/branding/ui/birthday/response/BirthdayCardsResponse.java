package kr.mashup.branding.ui.birthday.response;

import lombok.Getter;
import lombok.Value;

import java.util.List;

@Getter
@Value(staticConstructor = "of")
public class BirthdayCardsResponse {
    List<BirthdayCardResponse> birthdayCards;
}
