package kr.mashup.branding.domain.attendance;

import com.sun.istack.NotNull;
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

    // TODO : 최민성 구현 예정
//    @OneToMany(mappedBy = "generation")
//    private final List<Invite> invites = new ArrayList<>();

    public void addSchedule(Schedule schedule){
        if(schedules.contains(schedule)){
            return;
        }
        this.schedules.add(schedule);
    }

    public static Generation of(Integer number, LocalDate startedAt, LocalDate endedAt){
        return new Generation(number, startedAt, endedAt);
    }

    private Generation(int number, LocalDate startedAt, LocalDate endedAt){

        checkStartBeforeOrEqualEnd(startedAt, endedAt);

        if(number<=0){
            throw new IllegalArgumentException();
        }

        this.number = number;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
    }

    private void checkStartBeforeOrEqualEnd(LocalDate startedAt, LocalDate ended_at) {
        if(!DateUtil.isStartBeforeOrEqualEnd(startedAt, ended_at)){
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
