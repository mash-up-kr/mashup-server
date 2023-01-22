package kr.mashup.branding.repository.schedule;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.exception.BadRequestException;
import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.schedule.Schedule;
import kr.mashup.branding.util.QueryUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

import static kr.mashup.branding.domain.generation.QGeneration.generation;
import static kr.mashup.branding.domain.member.QMember.member;
import static kr.mashup.branding.domain.member.QMemberGeneration.memberGeneration;
import static kr.mashup.branding.domain.schedule.QSchedule.schedule;

@RequiredArgsConstructor
public class ScheduleRepositoryCustomImpl implements ScheduleRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Schedule> findByGeneration(Generation _generation, Pageable pageable) {
        final Sort sort = pageable.getSortOr(Sort.by(Sort.Direction.ASC, "startedAt"));

        final QueryResults<Schedule> queryResults = queryFactory
            .selectFrom(schedule)
            .join(schedule.generation, generation).fetchJoin()
            .where(generation.eq(_generation))
            .orderBy(getOrderSpecifier(sort))
            .fetchResults();

        return QueryUtils.toPage(queryResults, pageable);
    }

    private OrderSpecifier[] getOrderSpecifier(Sort sort) {

        final List<OrderSpecifier> orderSpecifiers = new ArrayList<>();

        for (Sort.Order order : sort) {
            Sort.Direction direction = order.getDirection();
            String field = order.getProperty();
            Order qOrder = direction.isAscending() ? Order.ASC : Order.DESC;

            final OrderSpecifier orderSpecifier
                = new OrderSpecifier(qOrder, Expressions.path(Object.class, schedule, field));

            orderSpecifiers.add(orderSpecifier);
        }
        return orderSpecifiers.toArray(new OrderSpecifier[0]);
    }
}
