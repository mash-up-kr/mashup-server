package kr.mashup.branding.domain.content;

import com.sun.istack.NotNull;
import kr.mashup.branding.domain.BaseEntity;
import kr.mashup.branding.domain.event.Event;
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
public class Content extends BaseEntity {

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "event_id")
    private Event event;

    @NotNull
    private String content;

    public static Content of(Event event, String content){
        return new Content(event, content);
    }

    private Content(Event event, String content){

        this.event = event;
        event.addContent(this);
        this.content = content;
    }

    public void changeContent(String newContent){
        Assert.hasText(newContent, "");
        this.content = newContent;
    }


}
