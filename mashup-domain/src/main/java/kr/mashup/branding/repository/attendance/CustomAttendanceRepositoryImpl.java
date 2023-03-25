package kr.mashup.branding.repository.attendance;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.mashup.branding.domain.attendance.Attendance;
import kr.mashup.branding.domain.attendance.QAttendance;
import kr.mashup.branding.domain.member.QMember;
import kr.mashup.branding.domain.schedule.Event;
import kr.mashup.branding.domain.schedule.QEvent;
import kr.mashup.branding.domain.schedule.QSchedule;
import kr.mashup.branding.domain.schedule.Schedule;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static kr.mashup.branding.domain.attendance.QAttendance.attendance;
import static kr.mashup.branding.domain.member.QMember.member;
import static kr.mashup.branding.domain.schedule.QEvent.event;
import static kr.mashup.branding.domain.schedule.QSchedule.schedule;

@RequiredArgsConstructor
public class CustomAttendanceRepositoryImpl implements CustomAttendanceRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<Attendance> findBySchedule(Schedule _schedule) {
        return jpaQueryFactory
                .select(attendance)
                .from(attendance)
                .innerJoin(attendance.event, event).fetchJoin()
                .innerJoin(attendance.member, member).fetchJoin()
                .innerJoin(event.schedule, schedule).fetchJoin()
                .where(schedule.eq(_schedule))
                .fetch();
    }

    @Override
    public List<Attendance> findAllByEvent(Event _event) {
        return jpaQueryFactory
                .select(attendance)
                .from(attendance)
                .innerJoin(attendance.event, event).fetchJoin()
                .innerJoin(attendance.member, member).fetchJoin()
                .where(event.eq(_event))
                .fetch();
    }
}
