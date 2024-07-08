package kr.mashup.branding.domain.generation;

import kr.mashup.branding.domain.BaseEntity;
import kr.mashup.branding.util.DateRange;
import kr.mashup.branding.util.DateUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Generation extends BaseEntity {

    @NotNull
    private Integer number;

    @NotNull
    private LocalDate startedAt;

    @NotNull
    private LocalDate endedAt;


    public static Generation of(Integer number, DateRange dateRange){
        return new Generation(number, dateRange);
    }

    private Generation(int number, DateRange dateRange){

        if(number<=0){
            throw new IllegalArgumentException();
        }

        this.number = number;
        this.startedAt = dateRange.getStart().toLocalDate();
        this.endedAt = dateRange.getEnd().toLocalDate();
    }

    private void checkStartBeforeOrEqualEnd(LocalDate startedAt, LocalDate endedAt) {
        if(!DateUtil.isStartBeforeOrEqualEnd(startedAt.atStartOfDay(), endedAt.atStartOfDay())){
            throw new IllegalArgumentException();
        }
    }
    public void changeDate(DateRange dateRange){
        this.startedAt = dateRange.getStart().toLocalDate();
        this.endedAt = dateRange.getEnd().toLocalDate();
    }


    public void changeStartDate(LocalDate newStartDate){

        if(!DateUtil.isStartBeforeOrEqualEnd(newStartDate.atStartOfDay(), endedAt.atStartOfDay())){
            throw new IllegalArgumentException("유효하지 않은 시작시간과 끝나는 시간입니다.");
        }

        this.startedAt = newStartDate;
    }


    public void changeEndedDate(LocalDate newEndedDate){

        if(!DateUtil.isStartBeforeOrEqualEnd(startedAt.atStartOfDay(), newEndedDate.atStartOfDay())){
            throw new IllegalArgumentException("유효하지 않은 시작시간과 끝나는 시간입니다.");
        }

        this.endedAt = newEndedDate;
    }

    public boolean isInProgress(LocalDate baseTime){
        return DateUtil.isInTime(startedAt, endedAt, baseTime);
    }

    public GenerationStatus getStatus(){

        // 현재 일자 기준으로 기수 종료일자가 미래이거나 같은 경우
        if (DateUtil.isStartBeforeOrEqualEnd(LocalDate.now(), endedAt)) {
            return GenerationStatus.ON_GOING;
        }

        return GenerationStatus.DONE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Generation that = (Generation) o;
        return getNumber().equals(that.getNumber()) && getStartedAt().equals(that.getStartedAt()) && getEndedAt().equals(that.getEndedAt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getNumber(), getStartedAt(), getEndedAt());
    }
}
