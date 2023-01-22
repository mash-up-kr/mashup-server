package kr.mashup.branding.repository.emailnotification;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.email.EmailNotification;
import kr.mashup.branding.domain.exception.BadRequestException;
import kr.mashup.branding.util.QueryUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static kr.mashup.branding.domain.adminmember.entity.QAdminMember.adminMember;
import static kr.mashup.branding.domain.email.QEmailNotification.emailNotification;
import static kr.mashup.branding.domain.generation.QGeneration.generation;

@RequiredArgsConstructor
public class EmailNotificationRepositoryCustomImpl implements EmailNotificationRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<EmailNotification> findBySearchWord(Optional<String> searchWord, Pageable pageable) {

        final Sort sort = pageable.getSortOr(Sort.by(Sort.Direction.DESC, "createdAt"));

        QueryResults<EmailNotification> results = queryFactory
                .selectFrom(emailNotification)
                .innerJoin(emailNotification.sender, adminMember).fetchJoin()
                .innerJoin(emailNotification.generation, generation).fetchJoin()
                .where(isContainSearchWord(searchWord))
                .orderBy(getOrderSpecifier(sort))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetchResults();

        return QueryUtils.toPage(results, pageable);
    }

    private BooleanExpression isContainSearchWord(Optional<String> searchWord) {
        if (searchWord.isEmpty()) {
            return null;
        }

        final String searchWordParam = searchWord.get();
        return emailNotification.memo.contains(searchWordParam);
    }

    private OrderSpecifier[] getOrderSpecifier(Sort sort) {

        final List<OrderSpecifier> orderSpecifiers = new ArrayList<>();

        for (Sort.Order order : sort) {
            final Sort.Direction direction = order.getDirection();
            final String field = order.getProperty();
            final Order qOrder = direction.isAscending() ? Order.ASC : Order.DESC;

            OrderSpecifier orderSpecifier = null;

            if (field.equals("createdAt")) {
                orderSpecifier = new OrderSpecifier(qOrder, Expressions.path(Object.class, emailNotification, field));
            } else if (field.equals("templateName")) {
                orderSpecifier = new OrderSpecifier(qOrder, Expressions.path(Object.class, emailNotification, "emailTemplateName"));
            } else {
                throw new BadRequestException(ResultCode.BAD_REQUEST);
            }

            orderSpecifiers.add(orderSpecifier);
        }
        return orderSpecifiers.toArray(new OrderSpecifier[0]);
    }
}
