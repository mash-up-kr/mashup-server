package kr.mashup.branding.domain.attendance;

import kr.mashup.branding.util.DateUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule extends BaseEntity{

    private String name;

    private LocalDateTime startedAt;

    private LocalDateTime endedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "generation_id")
    private Generation generation;

    @OneToMany(mappedBy = "schedule")
    private final List<Event> events = new ArrayList<>();

    private String createdBy;
    private String updatedBy;


    public Schedule(Generation generation, String name, LocalDateTime startedAt, LocalDateTime endedAt, String createdBy){
        Assert.notNull(generation,"기수가 비어있을 수 없습니다.");
        Assert.notNull(startedAt,"시작시각이 비어있을 수 없습니다.");
        Assert.notNull(endedAt,"끝나는 시각이 비어있을 수 없습니다.");
        Assert.hasText(name, "이름이 비어있을 수 없습니다.");
        Assert.hasText(createdBy, "생성자 이름이 비어있을 수 없습니다.");

        checkStartBeforeOrEqualEnd(startedAt, endedAt);
        checkGenerationRangeContainScheduleRange(generation,startedAt, endedAt);

        this.generation = generation;
        this.generation.addSchedule(this);
        this.name = name;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
        this.createdBy = createdBy;
    }

    public void changeName(String newName){
        Assert.hasText(newName,"이름이 비어있을 수 없습니다.");
        this.name = newName;
    }

    public void changeStartDate(LocalDateTime newStartDate){
        checkStartBeforeOrEqualEnd(newStartDate, endedAt);
        checkGenerationRangeContainScheduleRange(this.generation,newStartDate, endedAt);
        this.startedAt = newStartDate;
    }

    public void changeEndDate(LocalDateTime newEndDate){
        checkStartBeforeOrEqualEnd(startedAt, newEndDate);
        checkGenerationRangeContainScheduleRange(this.generation,startedAt, newEndDate);
        this.endedAt = newEndDate;
    }

    public void changeUpdateBy(String updatedBy){
        Assert.hasText(updatedBy, "수정자 이름이 비었을 수 없습니다.");
        this.updatedBy = updatedBy;
    }



    /**
     *
     * Private Methods
     */
    private void checkGenerationRangeContainScheduleRange(Generation generation, LocalDateTime startedAt, LocalDateTime endedAt) {
        LocalDate genStartDate = generation.getStartedAt();
        LocalDate genEndedDate = generation.getEnded_at();

        if(!DateUtil.isContainDateRange(genStartDate, genEndedDate, startedAt.toLocalDate(), endedAt.toLocalDate())){
            throw new IllegalArgumentException("기수를 벗어난 유효하지 않은 시간입니다.");
        }
    }

    private void checkStartBeforeOrEqualEnd(LocalDateTime startedAt, LocalDateTime endedAt) {
        if(!DateUtil.isStartBeforeOrEqualEnd(startedAt, endedAt)){
            throw new IllegalArgumentException("유효하지 않은 시작시간과 끝나는 시간입니다.");
        }
    }

}
