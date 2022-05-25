package kr.mashup.branding.domain.attendance;

import kr.mashup.branding.util.DateUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Generation extends BaseEntity{

    private Integer number;

    private LocalDate startedAt;

    private LocalDate ended_at;

    @OneToMany(mappedBy = "generation")
    private final List<Schedule> schedules = new ArrayList<>();

    public void addSchedule(Schedule schedule){
        this.schedules.add(schedule);
    }

    public Generation(int number, LocalDate startedAt, LocalDate ended_at){
        if(DateUtil.isStartBeforeOrEqualEnd(startedAt, ended_at)){
            throw new IllegalArgumentException();
        }
        if(number<=0){
            throw new IllegalArgumentException();
        }
        this.number = number;
        this.startedAt = startedAt;
        this.ended_at = ended_at;
    }

    public void changeStartDate(LocalDate newStartDate){

        if(!DateUtil.isStartBeforeOrEqualEnd(newStartDate, ended_at)){
            throw new IllegalArgumentException("유효하지 않은 시작시간과 끝나는 시간입니다.");
        }

        //스케줄 중 변경되는 범위에 벗어나는 것이 없는지 체크
        boolean isOutOfSchedule = false;
        StringBuilder outOfSchedules = new StringBuilder();
        for(Schedule schedule : schedules){
            LocalDate scheduleStartedDate = schedule.getStartedAt().toLocalDate();
            LocalDate scheduleEndedDate = schedule.getEndedAt().toLocalDate();
            if(!DateUtil.isContainDateRange(newStartDate, ended_at, scheduleStartedDate, scheduleEndedDate)){
                isOutOfSchedule = true;
                outOfSchedules.append(schedule.getName()).append(" ");
            }
        }
        if(isOutOfSchedule){
            throw new IllegalArgumentException("변경된 날짜 범위를 벗어나는 일정이 있습니다: "+outOfSchedules);
        }

        this.startedAt = newStartDate;
    }

    public void changeEndedDate(LocalDate newEndedDate){

        if(!DateUtil.isStartBeforeOrEqualEnd(startedAt, newEndedDate)){
            throw new IllegalArgumentException("유효하지 않은 시작시간과 끝나는 시간입니다.");
        }

        //스케줄 중 변경되는 범위에 벗어나는 것이 없는지 체크
        boolean isOutOfSchedule = false;
        StringBuilder outOfSchedules = new StringBuilder();
        for(Schedule schedule : schedules){
            LocalDate scheduleStartedDate = schedule.getStartedAt().toLocalDate();
            LocalDate scheduleEndedDate = schedule.getEndedAt().toLocalDate();
            if(!DateUtil.isContainDateRange(startedAt, newEndedDate, scheduleStartedDate, scheduleEndedDate)){
                isOutOfSchedule = true;
                outOfSchedules.append(schedule.getName()).append(" ");
            }
        }
        if(isOutOfSchedule){
            throw new IllegalArgumentException("변경된 날짜 범위를 벗어나는 일정이 있습니다: "+outOfSchedules);
        }

        this.ended_at = newEndedDate;
    }

}
