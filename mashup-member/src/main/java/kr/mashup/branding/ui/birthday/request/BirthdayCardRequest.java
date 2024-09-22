package kr.mashup.branding.ui.birthday.request;

import lombok.Getter;

import javax.validation.constraints.Size;

@Getter
public class BirthdayCardRequest {
    private final long recipientMemberId;
    @Size(max = 50, message = "생일 축하 메시지는 50글자를 초과할 수 없습니다.")
    private final String message;
    private final String imageUrl;

    public BirthdayCardRequest(long recipientMemberId, String message, String imageUrl) {
        this.recipientMemberId = recipientMemberId;
        this.message = message;
        this.imageUrl = removeQueryParameters(imageUrl);
    }

    private String removeQueryParameters(String url) {
        int queryParamIndex = url.indexOf("?");
        if (queryParamIndex != -1) {
            return url.substring(0, queryParamIndex);
        }
        return url;
    }
}
