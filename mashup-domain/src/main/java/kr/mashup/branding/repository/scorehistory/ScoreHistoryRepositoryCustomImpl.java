package kr.mashup.branding.repository.scorehistory;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.mashup.branding.domain.scorehistory.ScoreHistory;
import kr.mashup.branding.domain.scorehistory.ScoreType;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static kr.mashup.branding.domain.scorehistory.QScoreHistory.scoreHistory;

@RequiredArgsConstructor
public class ScoreHistoryRepositoryCustomImpl implements ScoreHistoryRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ScoreHistory> retrieveAttendanceScoreByDate(LocalDate date) {
        return queryFactory
                .selectFrom(scoreHistory)
                .where(scoreHistory.date.between(
                                date.atStartOfDay(),
                                LocalDateTime.of(date, LocalTime.MAX).withNano(0))
                        .and(scoreHistory.name.in(
                                ScoreType.ATTENDANCE.getName(),
                                ScoreType.ABSENT.getName(),
                                ScoreType.LATE.getName())))
                .fetch();
    }
}
