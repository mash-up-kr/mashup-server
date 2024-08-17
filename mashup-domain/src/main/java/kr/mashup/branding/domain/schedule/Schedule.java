package kr.mashup.branding.domain.schedule;

import kr.mashup.branding.domain.BaseEntity;
import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.member.Platform;
import kr.mashup.branding.domain.schedule.exception.ScheduleAlreadyPublishedException;
import kr.mashup.branding.util.DateRange;
import kr.mashup.branding.util.DateUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("startedAt")
    private List<Event> eventList = new ArrayList<>();

    private LocalDateTime publishedAt;

    /*
    ScoreHistory 배치가 수행된 스케줄인지의 여부를 판단하기 위한 컬럼
     */
    private Boolean isCounted;

    @Enumerated(EnumType.STRING)
    private ScheduleStatus status;

    @Embedded
    private Location location;

    @Enumerated(EnumType.STRING)
    private ScheduleType scheduleType;

    private String notice;

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String updatedBy;

    public static Schedule of(Generation generation, String name, DateRange dateRange, Location location, ScheduleType scheduleType, String notice) {
        return new Schedule(generation, name, dateRange, location, scheduleType, notice);
    }

    public Schedule(Generation generation, String name, DateRange dateRange, Location location, ScheduleType scheduleType, String notice) {
        checkStartBeforeOrEqualEnd(dateRange.getStart(), dateRange.getEnd());

        this.generation = generation;
        this.name = name;
        this.startedAt = dateRange.getStart();
        this.endedAt = dateRange.getEnd();
        this.status = ScheduleStatus.ADMIN_ONLY;
        this.isCounted = false; // 기본값은 false 로 설정(배치가 수행되지 않음)
        this.location = location;
        this.scheduleType = scheduleType;
        this.notice = notice;
    }

    public void publishSchedule() {
        if (status == ScheduleStatus.PUBLIC) {
            throw new ScheduleAlreadyPublishedException();
        }
        this.status = ScheduleStatus.PUBLIC;
        this.publishedAt = LocalDateTime.now();
    }

    public void changeGeneration(Generation generation) {
        this.generation = generation;
    }

    public void hide() {
        // TODO: 채워넣기
        if (status != ScheduleStatus.PUBLIC) {

        }
        if (startedAt.isBefore(LocalDateTime.now())) {

        }
        this.status = ScheduleStatus.ADMIN_ONLY;
        this.publishedAt = null;
    }

    public void addEvent(Event event) {
        this.eventList.add(event);
    }

    public void clearEvent() {
        this.eventList.clear();
    }

    public void changeName(String newName) {
        Assert.hasText(newName, "이름이 비어있을 수 없습니다.");
        this.name = newName;
    }

    public void changeDate(LocalDateTime startDate, LocalDateTime endDate) {
        checkStartBeforeOrEqualEnd(startDate, endDate);
        this.startedAt = startDate;
        this.endedAt = endDate;
    }

    public void changeLocation(Location location) {
        this.location = location;
    }

    public void changeStartDate(LocalDateTime newStartDate) {
        checkStartBeforeOrEqualEnd(newStartDate, endedAt);
        this.startedAt = newStartDate;
    }

    public void changeEndDate(LocalDateTime newEndDate) {
        checkStartBeforeOrEqualEnd(startedAt, newEndDate);
        this.endedAt = newEndDate;
    }

    public void changeScheduleType(ScheduleType scheduleType) {
        this.scheduleType = scheduleType;
    }

    private void checkStartBeforeOrEqualEnd(LocalDateTime startedAt, LocalDateTime endedAt) {
        if (!DateUtil.isStartBeforeOrEqualEnd(startedAt, endedAt)) {
            throw new IllegalArgumentException("유효하지 않은 시작시간과 끝나는 시간입니다.");
        }
    }

    public void changeIsCounted(Boolean isCounted) {
        this.isCounted = isCounted;
    }

    public Boolean isShowable() {
        return this.status == ScheduleStatus.PUBLIC;
    }

    public Boolean isOnline() {
        return this.location == null || this.location.getLatitude() == null || this.location.getLongitude() == null;
    }

    public Boolean checkAvailabilityByPlatform(Platform platform) {
        switch (scheduleType) {
            case ALL:
                return true;
            case SPRING:
                return platform == Platform.SPRING;
            case IOS:
                return platform == Platform.IOS;
            case DESIGN:
                return platform == Platform.DESIGN;
            case WEB:
                return platform == Platform.WEB;
            case NODE:
                return platform == Platform.NODE;
            case ANDROID:
                return platform == Platform.ANDROID;
            default:
                return false;
        }
    }

    public void updateNotice(String notice) {
        this.notice = notice;
    }
}
