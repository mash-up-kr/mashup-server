package kr.mashup.branding.domain.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.util.StringUtils;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPQLQuery;

public class ApplicationRepositoryImpl extends QuerydslRepositorySupport implements ApplicationRepositoryCustom {
    private final QApplication qApplication = QApplication.application;

    public ApplicationRepositoryImpl() {
        super(Application.class);
    }

    @Override
    public Page<Application> findBy(ApplicationQueryVo applicationQueryVo) {

        JPQLQuery<Application> query = from(qApplication);
        // where
        Optional<BooleanExpression> booleanExpression = toBooleanExpression(applicationQueryVo);
        if (booleanExpression.isPresent()) {
            query = query.where(booleanExpression.get());
        }
        // sort
        List<OrderSpecifier> orderSpecifiers = toOrderSpecifiers(applicationQueryVo.getPageable().getSort());
        if (!orderSpecifiers.isEmpty()) {
            query = query.orderBy(orderSpecifiers.toArray(new OrderSpecifier[0]));
        }
        // paging
        int pageSize = applicationQueryVo.getPageable().getPageSize();
        query.offset(applicationQueryVo.getPageable().getOffset());
        query.limit(pageSize);
        QueryResults<Application> queryResults = query.fetchResults();
        return new PageImpl<>(
            queryResults.getResults(),
            PageRequest.of(((int)queryResults.getTotal() / pageSize), pageSize),
            queryResults.getTotal()
        );
    }

    private Optional<BooleanExpression> toBooleanExpression(ApplicationQueryVo applicationQueryVo) {
        List<BooleanExpression> booleanExpressions = new ArrayList<>();
        if (applicationQueryVo.getTeamId() != null) {
            booleanExpressions.add(qApplication.applicationForm.team.teamId.eq(applicationQueryVo.getTeamId()));
        }
        if (applicationQueryVo.getScreeningStatus() != null) {
            booleanExpressions.add(
                qApplication.applicationResult.screeningStatus.eq(applicationQueryVo.getScreeningStatus()));
        }
        if (applicationQueryVo.getInterviewStatus() != null) {
            booleanExpressions.add(
                qApplication.applicationResult.interviewStatus.eq(applicationQueryVo.getInterviewStatus()));
        }
        if (applicationQueryVo.getConfirmationStatus() != null) {
            booleanExpressions.add(qApplication.confirmation.status.eq(applicationQueryVo.getConfirmationStatus()));
        }
        if (StringUtils.hasText(applicationQueryVo.getSearchWord())) {
            booleanExpressions.add(
                qApplication.applicant.name.contains(applicationQueryVo.getSearchWord())
                    .or(qApplication.applicant.phoneNumber.contains(applicationQueryVo.getSearchWord()))
            );
        }
        return booleanExpressions.stream().reduce(BooleanExpression::and);
    }

    private List<OrderSpecifier> toOrderSpecifiers(Sort sort) {
        return sort.stream()
            .map(it -> new OrderSpecifier(
                toOrder(it),
                Expressions.path(Object.class, qApplication, it.getProperty())
            ))
            .collect(Collectors.toList());
    }

    private Order toOrder(Sort.Order sortOrder) {
        return sortOrder.getDirection().isAscending() ? Order.ASC : Order.DESC;
    }
}
