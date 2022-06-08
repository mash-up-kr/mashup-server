package kr.mashup.branding.repository.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import kr.mashup.branding.domain.application.Application;
import kr.mashup.branding.domain.application.ApplicationQueryVo;
import kr.mashup.branding.domain.application.ApplicationStatus;
import kr.mashup.branding.domain.application.QApplication;
import kr.mashup.branding.domain.application.result.ApplicationInterviewStatus;
import kr.mashup.branding.domain.application.result.ApplicationScreeningStatus;
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
            applicationQueryVo.getPageable().getSortOr(Sort.by(Sort.Order.desc("submittedAt"))));
        if (!CollectionUtils.isEmpty(orderSpecifiers)) {
            query.orderBy(orderSpecifiers.toArray(new OrderSpecifier[0]));
        }
        // paging
        int pageSize = applicationQueryVo.getPageable().getPageSize();
        query.offset(applicationQueryVo.getPageable().getOffset());
        query.limit(pageSize);
        return QueryUtils.toPage(query.fetchResults(), applicationQueryVo.getPageable());
    }

    private Optional<BooleanExpression> resolveBooleanExpression(ApplicationQueryVo applicationQueryVo) {
        List<BooleanExpression> booleanExpressions = new ArrayList<>();

        if (applicationQueryVo.getTeamId() != null) {
            booleanExpressions.add(qApplication.applicationForm.team.teamId.eq(applicationQueryVo.getTeamId()));
        }

        if (applicationQueryVo.getScreeningStatus() != null) {
            booleanExpressions.add(
                qApplication.applicationResult.screeningStatus.eq(applicationQueryVo.getScreeningStatus()));

            // 서류 합격일 경우에 최종 합격도 서류 합격에 속하기 때문에 필터링에 같이 추출, 따라서 인터뷰 결과가 패스는 제거
            if(applicationQueryVo.getScreeningStatus() == ApplicationScreeningStatus.PASSED) {
                booleanExpressions.add(
                    qApplication.applicationResult.interviewStatus.ne(ApplicationInterviewStatus.PASSED));
            }
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

        if (!applicationQueryVo.getIsShowAll()) {
            booleanExpressions.add(qApplication.status.eq(ApplicationStatus.SUBMITTED));
        }
        return booleanExpressions.stream().reduce(BooleanExpression::and);
    }
}
