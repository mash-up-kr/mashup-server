package kr.mashup.branding.ui.birthday.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Size;

@Getter
@RequiredArgsConstructor
public class BirthdayCardRequest {
    private final long recipientMemberId;
    @Size(max = 50, message = "생일 축하 메시지는 50글자를 초과할 수 없습니다.")
    private final String message;
    private final String imageUrl;
}
