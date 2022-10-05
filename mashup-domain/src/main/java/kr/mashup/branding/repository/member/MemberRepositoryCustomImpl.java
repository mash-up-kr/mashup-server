package kr.mashup.branding.repository.member;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.exception.BadRequestException;
import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.member.MemberStatus;
import kr.mashup.branding.domain.member.Platform;
import kr.mashup.branding.domain.member.QMemberGeneration;
import kr.mashup.branding.util.QueryUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

import static kr.mashup.branding.domain.member.QMember.member;
import static kr.mashup.branding.domain.member.QMemberGeneration.memberGeneration;
import static kr.mashup.branding.domain.scorehistory.QScoreHistory.scoreHistory;

@RequiredArgsConstructor
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    //    @Query("select m from Member m join m.memberGenerations mg on mg.generation = :generation join ScoreHistory sh on sh.member = m where m.status = 'ACTIVE' group by m order by sum(sh.score) desc")
    private final NumberPath<Double> sumAlias = Expressions.numberPath(Double.class, "score");

    @Override
    public Page<MemberScoreQueryResult> findAllActiveByGeneration(Generation generation, Platform platform, String searchName, Pageable pageable) {
        QueryResults<MemberScoreQueryResult> results = queryFactory
            .select(Projections.constructor(MemberScoreQueryResult.class, member, memberGeneration.platform, scoreHistory.score.sum().coalesce(0d).as(sumAlias)))
            .from(member)
            .leftJoin(scoreHistory).on(scoreHistory.member.eq(member).and(scoreHistory.generation.eq(generation)).and(scoreHistory.isCanceled.eq(false)))
            .join(memberGeneration).on(memberGeneration.member.eq(member).and(memberGeneration.generation.eq(generation)))
            .where(nameContains(searchName), member.status.eq(MemberStatus.ACTIVE), platformEq(platform))
            .groupBy(member, memberGeneration)
            .orderBy(getOrderSpecifier(pageable))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetchResults();

        return QueryUtils.toPage(results, pageable);
    }

    private OrderSpecifier[] getOrderSpecifier(Pageable pageable) {
        List<OrderSpecifier> orderSpecifiers = new ArrayList<>();
        for (Sort.Order order : pageable.getSort()) {
            Sort.Direction direction = order.getDirection();
            String field = order.getProperty();
            Order qOrder = direction.isAscending() ? Order.ASC : Order.DESC;

            OrderSpecifier orderSpecifier = switch (field) {
                case "name", "identification" -> new OrderSpecifier(qOrder, Expressions.path(Object.class, member, field));
                case "platform" -> new OrderSpecifier(qOrder, Expressions.path(Object.class, memberGeneration, field));
                case "score" -> new OrderSpecifier(qOrder, sumAlias);
                default -> throw new BadRequestException(ResultCode.BAD_REQUEST);
            };

            orderSpecifiers.add(orderSpecifier);
        }
        return orderSpecifiers.toArray(new OrderSpecifier[0]);
    }

    private BooleanExpression nameContains(String searchName) {

        return searchName != null ? member.name.contains(searchName) : null;
    }

    private BooleanExpression platformEq(Platform platform) {
        return platform != null ? memberGeneration.platform.eq(platform) : null;
    }

    @Getter
    @RequiredArgsConstructor
    public static class MemberScoreQueryResult {
        private final Member member;
        private final Platform platform;
        private final Double score;
    }
}

