package kr.mashup.branding.domain.generation;

import com.sun.istack.NotNull;
import kr.mashup.branding.domain.BaseEntity;
import kr.mashup.branding.domain.invite.Invite;
import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.schedule.Schedule;
import kr.mashup.branding.util.DateRange;
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
public class Generation extends BaseEntity {

    @NotNull
    private Integer number;

    @NotNull
    private LocalDate startedAt;

    @NotNull
    private LocalDate endedAt;

    @OneToMany(mappedBy = "generation")
    private final List<Schedule> schedules = new ArrayList<>();

    @OneToMany(mappedBy = "generation")
    private final List<Member> members = new ArrayList<>();

    @OneToMany(mappedBy = "generation")
    private final List<Invite> invites = new ArrayList<>();

    public void addSchedule(Schedule schedule){
        if(schedules.contains(schedule)){
            return;
        }
        this.schedules.add(schedule);
    }

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
        if(!DateUtil.isStartBeforeOrEqualEnd(startedAt, endedAt)){
            throw new IllegalArgumentException();
        }
    }

    public void changeStartDate(LocalDate newStartDate){

        if(!DateUtil.isStartBeforeOrEqualEnd(newStartDate, endedAt)){
            throw new IllegalArgumentException("유효하지 않은 시작시간과 끝나는 시간입니다.");
        }

        this.startedAt = newStartDate;
    }


    public void changeEndedDate(LocalDate newEndedDate){

        if(!DateUtil.isStartBeforeOrEqualEnd(startedAt, newEndedDate)){
            throw new IllegalArgumentException("유효하지 않은 시작시간과 끝나는 시간입니다.");
        }

        this.endedAt = newEndedDate;
    }

}
