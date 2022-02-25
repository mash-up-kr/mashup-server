package kr.mashup.branding.domain.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;

import kr.mashup.branding.util.QueryUtils;

public class ApplicationRepositoryImpl extends QuerydslRepositorySupport implements ApplicationRepositoryCustom {
    private final QApplication qApplication = QApplication.application;

    public ApplicationRepositoryImpl() {
        super(Application.class);
    }

    @Override
    public Page<Application> findBy(ApplicationQueryVo applicationQueryVo) {
        JPQLQuery<Application> query = from(qApplication);
        // where
        resolveBooleanExpression(applicationQueryVo).ifPresent(query::where);
        // sort
        List<OrderSpecifier> orderSpecifiers = QueryUtils.toOrderSpecifiers(qApplication,
            applicationQueryVo.getPageable().getSortOr(Sort.by(Sort.Order.asc("name"))));
        if (!CollectionUtils.isEmpty(orderSpecifiers)) {
            query.orderBy(orderSpecifiers.toArray(new OrderSpecifier[0]));
        }
        // paging
        int pageSize = applicationQueryVo.getPageable().getPageSize();
        query.offset(applicationQueryVo.getPageable().getOffset());
        query.limit(pageSize);
        return QueryUtils.toPage(query.fetchResults(), pageSize);
    }

    private Optional<BooleanExpression> resolveBooleanExpression(ApplicationQueryVo applicationQueryVo) {
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
}
