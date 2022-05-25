package kr.mashup.branding.domain.attendance;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Content extends BaseEntity{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event event;

    private String content;

    public Content(Event event, String content){
        Assert.notNull(event, "");
        Assert.hasText(content, "");

        this.event = event;
        event.addContent(this);
        this.content = content;
    }

    public void changeContent(String newContent){
        Assert.hasText(newContent, "");
        this.content = newContent;
    }


}
