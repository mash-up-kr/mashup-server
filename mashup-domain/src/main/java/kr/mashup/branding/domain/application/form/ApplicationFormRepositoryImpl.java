package kr.mashup.branding.domain.application.form;

import static kr.mashup.branding.util.QueryUtils.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;

import kr.mashup.branding.util.QueryUtils;

public class ApplicationFormRepositoryImpl extends QuerydslRepositorySupport
    implements ApplicationFormRepositoryCustom {

    private final QApplicationForm qApplicationForm = QApplicationForm.applicationForm;

    public ApplicationFormRepositoryImpl() {
        super(ApplicationForm.class);
    }

    @Override
    public Page<ApplicationForm> findByApplicationFormQueryVo(ApplicationFormQueryVo applicationFormQueryVo) {
        JPQLQuery<ApplicationForm> query = from(qApplicationForm);
        resolveCondition(applicationFormQueryVo).ifPresent(query::where);
        query.offset(applicationFormQueryVo.getPageable().getOffset());
        query.limit(applicationFormQueryVo.getPageable().getPageSize());
        List<OrderSpecifier> orderSpecifiers = toOrderSpecifiers(qApplicationForm,
            applicationFormQueryVo.getPageable().getSort());
        if (!CollectionUtils.isEmpty(orderSpecifiers)) {
            query.orderBy(orderSpecifiers.toArray(new OrderSpecifier[0]));
        }
        int pageSize = applicationFormQueryVo.getPageable().getPageSize();
        query.offset(applicationFormQueryVo.getPageable().getOffset());
        query.limit(pageSize);
        return QueryUtils.toPage(query.fetchResults(), pageSize);
    }

    private Optional<BooleanExpression> resolveCondition(ApplicationFormQueryVo applicationFormQueryVo) {
        List<BooleanExpression> booleanExpressions = new ArrayList<>();
        if (applicationFormQueryVo.getTeamId() != null) {
            booleanExpressions.add(qApplicationForm.team.teamId.eq(applicationFormQueryVo.getTeamId()));
        }
        if (StringUtils.hasText(applicationFormQueryVo.getSearchWord())) {
            booleanExpressions.add(qApplicationForm.name.contains(applicationFormQueryVo.getSearchWord()));
        }
        return booleanExpressions.stream().reduce(BooleanExpression::and);
    }
}
