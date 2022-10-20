package kr.mashup.branding.repository.member;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.exception.BadRequestException;
import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.member.MemberStatus;
import kr.mashup.branding.domain.member.Platform;
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
    private final NumberPath<Double> sumAlias = Expressions.numberPath(Double.class, "score");

    @Override
    public Page<MemberScoreQueryResult> findAllActiveByGeneration(Generation generation, Platform platform, String searchName, Pageable pageable) {
        //기본 정렬은 이름 기준
        Sort sort = pageable.getSortOr(Sort.by(Sort.Direction.ASC, "name"));

        QueryResults<MemberScoreQueryResult> results = queryFactory
            // score sum 은 numberPath sumAlias 로 접근한다. 정렬 필드에 score 가 있을 시에도 사용
            .select(Projections.constructor(MemberScoreQueryResult.class, member, memberGeneration.platform, scoreHistory.score.sum().coalesce(0d).as(sumAlias)))
            .from(member)
            // 점수가 없는 멤버도 있을 수 있으니 left join, 취소 여부는 on 절에서 판단해서 where 절에서 삭제되지 않게끔 함
            .leftJoin(scoreHistory).on(scoreHistory.member.eq(member).and(scoreHistory.generation.eq(generation)).and(scoreHistory.isCanceled.eq(false)))
            .join(memberGeneration).on(memberGeneration.member.eq(member).and(memberGeneration.generation.eq(generation)))
            .where(nameContains(searchName), member.status.eq(MemberStatus.ACTIVE), platformEq(platform))
            .groupBy(member, memberGeneration)
            .orderBy(getOrderSpecifier(sort))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetchResults();

        return QueryUtils.toPage(results, pageable);
    }

    private BooleanExpression nameContains(String searchName) {
        return searchName != null ? member.name.contains(searchName) : null;
    }

    private BooleanExpression platformEq(Platform platform) {
        return platform != null ? memberGeneration.platform.eq(platform) : null;
    }

    private OrderSpecifier[] getOrderSpecifier(Sort sort) {
        List<OrderSpecifier> orderSpecifiers = new ArrayList<>();
        for (Sort.Order order : sort) {
            Sort.Direction direction = order.getDirection();
            String field = order.getProperty();
            Order qOrder = direction.isAscending() ? Order.ASC : Order.DESC;

            OrderSpecifier orderSpecifier = null;

            if (field.equals("name") || field.equals("identification")) {
                orderSpecifier = new OrderSpecifier(qOrder, Expressions.path(Object.class, member, field));
            } else if (field.equals("platform")) {
                orderSpecifier = new OrderSpecifier(qOrder, Expressions.path(Object.class, memberGeneration, field));
            } else if (field.equals("score")) {
                orderSpecifier = new OrderSpecifier(qOrder, sumAlias);
            } else {
                throw new BadRequestException(ResultCode.BAD_REQUEST);
            }

            orderSpecifiers.add(orderSpecifier);
        }
        return orderSpecifiers.toArray(new OrderSpecifier[0]);
    }

    @Getter
    @RequiredArgsConstructor
    public static class MemberScoreQueryResult {
        private final Member member;
        private final Platform platform;
        private final Double score;
    }

    @Override
    public Long countActiveByPlatformAndGeneration(Platform platform, Generation generation) {
        Integer count = queryFactory
            .select(member.count())
            .from(member)
            .join(memberGeneration).on(memberGeneration.member.eq(member))
            .where(memberGeneration.platform.eq(platform)
                .and(memberGeneration.generation.eq(generation))
                .and(member.status.eq(MemberStatus.ACTIVE)))
            .fetch().size();
        return count.longValue();
    }

    @Override
    public List<Member> findActiveByPlatformAndGeneration(Platform platform, Generation generation) {
        return queryFactory
            .selectFrom(member)
            .join(memberGeneration).on(memberGeneration.member.eq(member))
            .where(memberGeneration.platform.eq(platform)
                .and(memberGeneration.generation.eq(generation))
                .and(member.status.eq(MemberStatus.ACTIVE)))
            .fetch();
    }

    @Override
    public Page<Member> findActiveByPlatformAndGeneration(Platform platform, Generation generation, Pageable pageable) {
        List<OrderSpecifier> orderSpecifiers = QueryUtils.toOrderSpecifiers(member, pageable.getSortOr(Sort.by(Sort.Order.asc("name"))));
        QueryResults<Member> fetchResults = queryFactory
            .selectFrom(member)
            .join(memberGeneration).on(memberGeneration.member.eq(member))
            .where(memberGeneration.platform.eq(platform)
                .and(memberGeneration.generation.eq(generation))
                .and(member.status.eq(MemberStatus.ACTIVE)))
            .orderBy(orderSpecifiers.isEmpty() ? null : orderSpecifiers.toArray(new OrderSpecifier[0]))
            .fetchResults();

        return QueryUtils.toPage(fetchResults, pageable);
    }


}

