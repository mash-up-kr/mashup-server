package kr.mashup.branding.domain.pushhistory;

import kr.mashup.branding.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PushHistory extends BaseEntity {

    private Long memberId;
    private String title;
    private String body;

    public static PushHistory of(Long memberId, String title, String body){
        return new PushHistory(memberId, title, body);
    }

    private PushHistory(Long memberId, String title, String body) {
        this.memberId = memberId;
        this.title = title;
        this.body = body;
    }
}
