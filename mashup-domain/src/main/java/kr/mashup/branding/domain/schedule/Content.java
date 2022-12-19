package kr.mashup.branding.domain.schedule;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.util.Assert;

import com.sun.istack.NotNull;

import kr.mashup.branding.domain.BaseEntity;
import kr.mashup.branding.util.DateUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Content extends BaseEntity {

    @NotNull
    private String title;

    @NotNull
    private String desc;

    @NotNull
    private LocalDateTime startedAt;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "event_id")
    private Event event;

    public static Content of(Event event, String title, String desc, LocalDateTime startedAt) {
        return new Content(title, desc, startedAt, event);
    }

    private Content(String title, String desc, LocalDateTime startedAt, Event event) {
        checkStartIsInTime(event.getStartedAt(), event.getEndedAt(), startedAt);

        this.title = title;
        this.desc = desc;
        this.startedAt = startedAt;
        this.event = event;
        event.addContent(this);
    }

    public void changeDesc(String desc) {
        Assert.hasText(desc, "");
        this.desc = desc;
    }

    private void checkStartIsInTime(LocalDateTime eventStartedAt, LocalDateTime eventEndedAt, LocalDateTime startedAt) {
        if (!DateUtil.isInTime(eventStartedAt, eventEndedAt, startedAt)) {
            throw new IllegalArgumentException("유효하지 않은 시작시간입니다.");
        }
    }



}
