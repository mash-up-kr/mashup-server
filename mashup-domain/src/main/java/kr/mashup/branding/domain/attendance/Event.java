package kr.mashup.branding.domain.attendance;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Event extends BaseEntity{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    private LocalDateTime statedAt;

    private LocalDateTime endedAt;

    public Event(Schedule schedule, LocalDateTime statedAt, LocalDateTime endedAt){

        Assert.notNull(schedule, "");
    }

}
