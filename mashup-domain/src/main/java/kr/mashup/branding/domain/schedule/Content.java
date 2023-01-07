package kr.mashup.branding.domain.schedule;

import com.sun.istack.NotNull;
import java.time.LocalDateTime;
import kr.mashup.branding.domain.BaseEntity;
import kr.mashup.branding.util.DateUtil;
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
    private String title;

    @NotNull
    private String description;

    @NotNull
    private LocalDateTime startedAt;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "event_id")
    private Event event;

    private Content(String title, String description, LocalDateTime startedAt, Event event) {
        checkStartIsInTime(event.getStartedAt(), event.getEndedAt(), startedAt);

        this.title = title;
        this.description = description;
        this.startedAt = startedAt;
        this.event = event;
        event.addContent(this);
    }

    public static Content of(Event event, String title, String description, LocalDateTime startedAt) {
        return new Content(title, description, startedAt, event);
    }

    public void changeDesc(String description) {
        Assert.hasText(description, "");
        this.description = description;
    }

    private void checkStartIsInTime(LocalDateTime eventStartedAt, LocalDateTime eventEndedAt, LocalDateTime startedAt) {
        if (!DateUtil.isInTime(eventStartedAt, eventEndedAt, startedAt)) {
            throw new IllegalArgumentException("유효하지 않은 시작시간입니다.");
        }
    }

}
