package kr.mashup.branding.repository.application.form;

import static kr.mashup.branding.domain.application.form.QApplicationForm.applicationForm;
import static kr.mashup.branding.util.QueryUtils.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.mashup.branding.domain.application.form.ApplicationForm;
import kr.mashup.branding.domain.application.form.ApplicationFormQueryVo;
import kr.mashup.branding.domain.application.form.QApplicationForm;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;

import kr.mashup.branding.util.QueryUtils;

@RequiredArgsConstructor
public class ApplicationFormRepositoryImpl implements ApplicationFormRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<ApplicationForm> findByApplicationFormQueryVo(ApplicationFormQueryVo applicationFormQueryVo) {

        final Long teamId = applicationFormQueryVo.getTeamId();
        final String searchWord = applicationFormQueryVo.getSearchWord();
        final Pageable pageable = applicationFormQueryVo.getPageable();
        final Long offset = pageable.getOffset();
        final int pageSize = pageable.getPageSize();
        final List<OrderSpecifier> orderSpecifiers
            = toOrderSpecifiers(
            applicationForm,
            applicationFormQueryVo.getPageable().getSortOr(Sort.by(Sort.Order.desc("updatedAt")))
        );

        QueryResults<ApplicationForm> fetchResults = queryFactory
            .selectFrom(applicationForm)
            .join(applicationForm.team)
            .fetchJoin()
            .where(teamIdEq(teamId), searchWordContains(searchWord))
            .orderBy(orderSpecifiers.isEmpty() ? null : orderSpecifiers.toArray(new OrderSpecifier[0]))
            .offset(offset)
            .limit(pageSize)
            .fetchResults();

        return QueryUtils.toPage(fetchResults, pageable);
    }

    private BooleanExpression teamIdEq(Long teamId) {
        return teamId != null ? applicationForm.team.teamId.eq(teamId) : null;
    }
    private BooleanExpression searchWordContains(String searchWord) {
        return StringUtils.hasText(searchWord) ? applicationForm.name.contains(searchWord) : null;
    }


}
