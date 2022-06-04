package kr.mashup.branding.domain.attendance;

import kr.mashup.branding.util.DateRange;
import kr.mashup.branding.util.DateUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule extends BaseEntity{

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
    private final List<Event> events = new ArrayList<>();

    @NotNull
    private String createdBy;

    @NotNull
    private String updatedBy;

    public static Schedule of(Generation generation, String name, DateRange dateRange, String createdBy){
        return new Schedule(generation, name, dateRange, createdBy);
    }

    public Schedule(Generation generation, String name, DateRange dateRange, String createdBy){

        checkStartBeforeOrEqualEnd(startedAt, endedAt);

        this.generation = generation;
        this.generation.addSchedule(this);
        this.name = name;
        this.startedAt = dateRange.getStart();
        this.endedAt = dateRange.getEnd();
        this.createdBy = createdBy;
    }

    public void changeName(String newName){
        Assert.hasText(newName,"이름이 비어있을 수 없습니다.");
        this.name = newName;
    }

    public void changeStartDate(LocalDateTime newStartDate){
        checkStartBeforeOrEqualEnd(newStartDate, endedAt);
        this.startedAt = newStartDate;
    }

    public void changeEndDate(LocalDateTime newEndDate){
        checkStartBeforeOrEqualEnd(startedAt, newEndDate);
        this.endedAt = newEndDate;
    }

    public void changeUpdateBy(String updatedBy){
        Assert.hasText(updatedBy, "수정자 이름이 비었을 수 없습니다.");
        this.updatedBy = updatedBy;
    }



    private void checkStartBeforeOrEqualEnd(LocalDateTime startedAt, LocalDateTime endedAt) {
        if(!DateUtil.isStartBeforeOrEqualEnd(startedAt, endedAt)){
            throw new IllegalArgumentException("유효하지 않은 시작시간과 끝나는 시간입니다.");
        }
    }

}
