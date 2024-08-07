package kr.mashup.branding.domain.pushhistory;

import kr.mashup.branding.domain.BaseEntity;
import kr.mashup.branding.domain.pushnoti.LinkType;
import kr.mashup.branding.domain.pushnoti.vo.PushType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PushHistory extends BaseEntity {

    private Long memberId;

    @Enumerated(EnumType.STRING)
    private PushType pushType;

    private String title;

    private String body;
    @Enumerated(EnumType.STRING)
    private LinkType linkType;

    public static PushHistory of(Long memberId, PushType pushType, String title, String body, LinkType linkType){
        return new PushHistory(memberId, pushType, title, body, linkType);
    }

    private PushHistory(Long memberId, PushType pushType, String title, String body, LinkType linkType) {
        this.memberId = memberId;
        this.pushType = pushType;
        this.title = title;
        this.body = body;
        this.linkType = linkType;
    }
}
