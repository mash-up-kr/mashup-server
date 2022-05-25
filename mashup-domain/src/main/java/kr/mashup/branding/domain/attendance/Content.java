package kr.mashup.branding.domain.attendance;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

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
        checkNotEmptyContent(content);

        this.event = event;
        this.content = content;
    }

    public void changeContent(String newContent){
        checkNotEmptyContent(newContent);
        this.content = newContent;
    }

    private void checkNotEmptyContent(String content) {
        if(!StringUtils.hasText(content)){
            throw new IllegalArgumentException("");
        }
    }

}
