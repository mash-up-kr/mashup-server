package kr.mashup.branding.repository.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.mashup.branding.domain.applicant.QApplicant;
import kr.mashup.branding.domain.application.Application;
import kr.mashup.branding.domain.application.ApplicationQueryVo;
import kr.mashup.branding.domain.application.ApplicationStatus;
import kr.mashup.branding.domain.application.QApplication;
import kr.mashup.branding.domain.application.confirmation.ApplicantConfirmationStatus;
import kr.mashup.branding.domain.application.confirmation.QConfirmation;
import kr.mashup.branding.domain.application.form.QApplicationForm;
import kr.mashup.branding.domain.application.result.ApplicationInterviewStatus;
import kr.mashup.branding.domain.application.result.ApplicationScreeningStatus;
import kr.mashup.branding.domain.application.result.QApplicationResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;

import kr.mashup.branding.util.QueryUtils;

import static kr.mashup.branding.domain.applicant.QApplicant.*;
import static kr.mashup.branding.domain.application.QApplication.application;
import static kr.mashup.branding.domain.application.confirmation.QConfirmation.*;
import static kr.mashup.branding.domain.application.form.QApplicationForm.*;
import static kr.mashup.branding.domain.application.result.QApplicationResult.*;

@RequiredArgsConstructor
public class ApplicationRepositoryImpl implements ApplicationRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Application> findBy(ApplicationQueryVo applicationQueryVo) {

        final Long teamId = applicationQueryVo.getTeamId(); // team 필터링
        final ApplicationScreeningStatus screeningStatus = applicationQueryVo.getScreeningStatus(); // 서류 조건 필터링
        final ApplicationInterviewStatus interviewStatus = applicationQueryVo.getInterviewStatus(); // 면접 조건 필터링
        final ApplicantConfirmationStatus confirmationStatus = applicationQueryVo.getConfirmationStatus(); // 확인 조건 필터링
        final String searchWord = applicationQueryVo.getSearchWord(); // 검색어 여부 필터링
        final Boolean isShowAll = applicationQueryVo.getIsShowAll(); // 미제출까지 포함 여부 필터링
        final long offset = applicationQueryVo.getPageable().getOffset();
        final int pageSize = applicationQueryVo.getPageable().getPageSize();

        List<OrderSpecifier> orderSpecifiers = QueryUtils.toOrderSpecifiers(application,
            applicationQueryVo.getPageable().getSortOr(Sort.by(Sort.Order.desc("submittedAt"))));

        JPAQuery<Application> query = queryFactory
            .select(application)
            .from(application)
                .join(applicant).fetchJoin()
                .join(applicationForm).fetchJoin()
                .join(applicationResult).fetchJoin()
                .join(confirmation).fetchJoin()
            .where(
                teamIdEq(teamId),
                screeningStatusEq(screeningStatus),
                interviewStatusEq(interviewStatus),
                confirmationStatusEq(confirmationStatus),
                searchWordContains(searchWord),
                isShowAllEq(isShowAll))
            .orderBy(!CollectionUtils.isEmpty(orderSpecifiers) ? null : orderSpecifiers.toArray(new OrderSpecifier[0]))
            .offset(offset)
            .limit(pageSize);


        return QueryUtils.toPage(query.fetchResults(), applicationQueryVo.getPageable());
    }


    private BooleanExpression screeningStatusEq(ApplicationScreeningStatus screeningStatus) {
        List<BooleanExpression> booleanExpressions = new ArrayList<>();
        if (screeningStatus != null) {
            booleanExpressions.add(application.applicationResult.screeningStatus.eq(screeningStatus));

            // 서류 합격일 경우에 최종 합격도 서류 합격에 속하기 때문에 필터링에 같이 추출, 따라서 인터뷰 결과가 패스는 제거
            if (screeningStatus == ApplicationScreeningStatus.PASSED) {
                booleanExpressions.add(application.applicationResult.interviewStatus.ne(ApplicationInterviewStatus.PASSED));
            }
        }
        return booleanExpressions.stream().reduce(BooleanExpression::and).orElse(null);
    }

    private BooleanExpression teamIdEq(Long teamId) {
        return teamId != null ? application.applicationForm.team.teamId.eq(teamId) : null;
    }

    private BooleanExpression interviewStatusEq(ApplicationInterviewStatus interviewStatus) {
        return interviewStatus != null ? application.applicationResult.interviewStatus.eq(interviewStatus) : null;
    }

    private BooleanExpression confirmationStatusEq(ApplicantConfirmationStatus confirmationStatus) {
        return confirmationStatus != null ? application.confirmation.status.eq(confirmationStatus) : null;
    }

    private BooleanExpression searchWordContains(String searchWord) {
        return StringUtils.hasText(searchWord) ?
            application.applicant.name.contains(searchWord)
                .or(application.applicant.phoneNumber.contains(searchWord)) : null;
    }

    private BooleanExpression isShowAllEq(Boolean isShowAll) {
        return (isShowAll != null && !isShowAll) ? application.status.eq(ApplicationStatus.SUBMITTED) : null;
    }

}
/**
 * Applicant 연관관계
 * many to one: applicant, applicationForm
 * one to one : applicationResult, confirmation
 */

