package kr.mashup.branding.repository.recruitmentschedule;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.recruitmentschedule.RecruitmentSchedule;
import kr.mashup.branding.domain.recruitmentschedule.RecruitmentScheduleEventName;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static kr.mashup.branding.domain.generation.QGeneration.generation;
import static kr.mashup.branding.domain.recruitmentschedule.QRecruitmentSchedule.recruitmentSchedule;

@RequiredArgsConstructor
public class RecruitmentScheduleRepositoryCustomImpl implements RecruitmentScheduleRepositoryCustom{

    private final JPAQueryFactory queryFactory;


    @Override
    public List<RecruitmentSchedule> findAllByGeneration(Generation _generation) {
        return queryFactory
            .selectFrom(recruitmentSchedule)
            .join(recruitmentSchedule.generation, generation).fetchJoin()
            .where(generation.eq(_generation))
            .fetch();
    }

    @Override
    public Optional<RecruitmentSchedule> findByEventName(Generation _generation,
                                                         RecruitmentScheduleEventName eventName) {
        RecruitmentSchedule fetchOne = queryFactory
            .selectFrom(recruitmentSchedule)
            .join(recruitmentSchedule.generation, generation).fetchJoin()
            .where(generation.eq(_generation)
                .and(recruitmentSchedule.eventName.eq(eventName)))
            .fetchOne();

        return fetchOne != null? Optional.of(fetchOne) : Optional.empty();
    }

}
