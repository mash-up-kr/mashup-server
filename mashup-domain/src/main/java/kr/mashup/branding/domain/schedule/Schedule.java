package kr.mashup.branding.domain.schedule;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.mashup.branding.domain.BaseEntity;
import kr.mashup.branding.domain.event.Event;
import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.util.DateRange;
import kr.mashup.branding.util.DateUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule extends BaseEntity {

    @NotNull
    private String name;

    @NotNull
    private LocalDateTime startedAt;

    @NotNull
    private LocalDateTime endedAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "generation_id")
    private Generation generation;

    @OneToMany(mappedBy = "schedule")
    @OrderBy("startedAt")
    private final List<Event> eventList = new ArrayList<>();

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String updatedBy;

    public static Schedule of(Generation generation, String name, DateRange dateRange) {
        return new Schedule(generation, name, dateRange);
    }

    public Schedule(Generation generation, String name, DateRange dateRange) {
        checkStartBeforeOrEqualEnd(dateRange.getStart(), dateRange.getEnd());

        this.generation = generation;
        this.name = name;
        this.startedAt = dateRange.getStart();
        this.endedAt = dateRange.getEnd();
    }

    public void addEvent(Event event) {
        if (eventList.contains(event)) {
            return;
        }
        this.eventList.add(event);
    }

    public void changeName(String newName) {
        Assert.hasText(newName, "????????? ???????????? ??? ????????????.");
        this.name = newName;
    }

    public void changeStartDate(LocalDateTime newStartDate) {
        checkStartBeforeOrEqualEnd(newStartDate, endedAt);
        this.startedAt = newStartDate;
    }

    public void changeEndDate(LocalDateTime newEndDate) {
        checkStartBeforeOrEqualEnd(startedAt, newEndDate);
        this.endedAt = newEndDate;
    }

    private void checkStartBeforeOrEqualEnd(LocalDateTime startedAt, LocalDateTime endedAt) {
        if (!DateUtil.isStartBeforeOrEqualEnd(startedAt, endedAt)) {
            throw new IllegalArgumentException("???????????? ?????? ??????????????? ????????? ???????????????.");
        }
    }

}
