package kr.mashup.branding.domain.attendance;

import kr.mashup.branding.util.DateUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalDateTime;

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

    private String createdBy;
    private String updatedBy;


    public Schedule(Generation generation, String name, LocalDateTime startedAt, LocalDateTime endedAt, String createdBy){
        Assert.notNull(generation,"기수가 비어있을 수 없습니다.");
        Assert.notNull(startedAt,"시작시각이 비어있을 수 없습니다.");
        Assert.notNull(endedAt,"끝나는 시각이 비어있을 수 없습니다.");

        checkStartBeforeOrEqualEnd(startedAt, endedAt);
        checkNameHasText(name);
        checkCreatedByHasText(createdBy);
        checkGenerationRangeContainScheduleRange(generation,startedAt, endedAt);

        this.generation = generation;
        this.generation.addSchedule(this);
        this.name = name;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
        this.createdBy = createdBy;
    }

    public void changeName(String newName){
        checkNameHasText(name);
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
    private void checkNameHasText(String name) {
        if(!StringUtils.hasText(name)){
            throw new IllegalArgumentException("스케줄 이름이 비어있을 수 없습니다.");
        }
    }
    private void checkCreatedByHasText(String createdBy) {
        if(!StringUtils.hasText(createdBy)){
            throw new IllegalArgumentException("이름이 비어있을 수 없습니다.");
        }
    }
    private void checkStartBeforeOrEqualEnd(LocalDateTime startedAt, LocalDateTime endedAt) {
        if(!DateUtil.isStartBeforeOrEqualEnd(startedAt, endedAt)){
            throw new IllegalArgumentException("유효하지 않은 시작시간과 끝나는 시간입니다.");
        }
    }

}
