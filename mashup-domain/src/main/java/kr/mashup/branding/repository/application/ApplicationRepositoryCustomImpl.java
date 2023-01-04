package kr.mashup.branding.repository.application;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.mashup.branding.domain.applicant.Applicant;
import kr.mashup.branding.domain.application.Application;
import kr.mashup.branding.domain.application.ApplicationQueryRequest;
import kr.mashup.branding.domain.application.ApplicationQueryVo;
import kr.mashup.branding.domain.application.ApplicationStatus;
import kr.mashup.branding.domain.application.QApplication;
import kr.mashup.branding.domain.application.confirmation.ApplicantConfirmationStatus;
import kr.mashup.branding.domain.application.confirmation.QConfirmation;
import kr.mashup.branding.domain.application.form.ApplicationForm;
import kr.mashup.branding.domain.application.form.QApplicationForm;
import kr.mashup.branding.domain.application.result.ApplicationInterviewStatus;
import kr.mashup.branding.domain.application.result.ApplicationScreeningStatus;
import kr.mashup.branding.domain.application.result.QApplicationResult;
import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.generation.QGeneration;
import kr.mashup.branding.domain.team.QTeam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;

import kr.mashup.branding.util.QueryUtils;

import static kr.mashup.branding.domain.applicant.QApplicant.applicant;
import static kr.mashup.branding.domain.application.QApplication.application;
import static kr.mashup.branding.domain.application.confirmation.QConfirmation.confirmation;
import static kr.mashup.branding.domain.application.form.QApplicationForm.*;
import static kr.mashup.branding.domain.application.result.QApplicationResult.applicationResult;
import static kr.mashup.branding.domain.generation.QGeneration.generation;
import static kr.mashup.branding.domain.team.QTeam.team;

@RequiredArgsConstructor
public class ApplicationRepositoryCustomImpl implements ApplicationRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Application> findBy(Generation _generation, ApplicationQueryVo applicationQueryVo) {
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

        JPAQuery<Application> query =
            queryFactory
                .selectFrom(application)
                .from(application)
                .join(application.applicant, applicant).fetchJoin()
                .join(application.applicationForm, applicationForm).fetchJoin()
                .join(applicationForm.team, team).fetchJoin()
                .join(team.generation, generation).fetchJoin()
                .join(application.applicationResult, applicationResult).fetchJoin()
                .join(application.confirmation, confirmation).fetchJoin()
                .where(
                    teamIdEq(teamId),
                    screeningStatusEq(screeningStatus),
                    interviewStatusEq(interviewStatus),
                    confirmationStatusEq(confirmationStatus),
                    searchWordContains(searchWord),
                    isShowAllEq(isShowAll),
                    generation.eq(_generation))
                .orderBy(CollectionUtils.isEmpty(orderSpecifiers) ? null : orderSpecifiers.toArray(new OrderSpecifier[0]))
                .offset(offset)
                .limit(pageSize);


        return QueryUtils.toPage(query.fetchResults(), applicationQueryVo.getPageable());
    }

    private BooleanExpression screeningStatusEq(ApplicationScreeningStatus screeningStatus) {
        List<BooleanExpression> booleanExpressions = new ArrayList<>();
        if (screeningStatus != null) {
            booleanExpressions.add(applicationResult.screeningStatus.eq(screeningStatus));
            // 서류 통과한 다음 면접 결과가 NOT RATED 에서 Pass 나 Fail 등이 되고 서탈이면 NOT APPLICABLE 이므로
            // interview status not rated 조건을 주어야 서류 결과만 난 지원자들을 볼 수 있다.
            booleanExpressions.add(applicationResult.interviewStatus.eq(ApplicationInterviewStatus.NOT_RATED));
            booleanExpressions.add(applicationResult.interviewStatus.eq(ApplicationInterviewStatus.NOT_APPLICABLE));
        }
        return booleanExpressions.stream().reduce(BooleanExpression::and).orElse(null);
    }

    private BooleanExpression teamIdEq(Long teamId) {
        return teamId != null ? team.teamId.eq(teamId) : null;
    }

    private BooleanExpression interviewStatusEq(ApplicationInterviewStatus interviewStatus) {
        return interviewStatus != null ? applicationResult.interviewStatus.eq(interviewStatus) : null;
    }

    private BooleanExpression confirmationStatusEq(ApplicantConfirmationStatus confirmationStatus) {
        return confirmationStatus != null ? confirmation.status.eq(confirmationStatus) : null;
    }

    private BooleanExpression searchWordContains(String searchWord) {
        return StringUtils.hasText(searchWord) ?
            applicant.name.contains(searchWord)
                .or(applicant.phoneNumber.contains(searchWord)) : null;
    }

    private BooleanExpression isShowAllEq(Boolean isShowAll) {
        return (isShowAll != null && !isShowAll) ? application.status.eq(ApplicationStatus.SUBMITTED) : null;
    }


    @Override
    public boolean existByApplicationForm(Long applicationFormId) {
        Application fetchFirst = queryFactory
            .selectFrom(application)
            .where(applicationForm.applicationFormId.eq(applicationFormId))
            .fetchFirst();
        return fetchFirst != null;
    }

    @Override
    public boolean existByGenerationAndApplicantAndApplicationStatus(
        Generation _generation,
        Long applicantId,
        ApplicationStatus status) {

        Application fetchFirst = queryFactory
            .selectFrom(application)
            .join(application.applicant, applicant)
            .join(application.applicationForm, applicationForm)
            .join(applicationForm.team, team)
            .join(team.generation, generation)
            .where(
                applicant.applicantId.eq(applicantId),
                application.status.eq(status),
                generation.id.eq(_generation.getId())
            )
            .fetchFirst();

        return fetchFirst != null;
    }

    @Override
    public List<Application> findByIdAndStatusIn(Long applicantId, Collection<ApplicationStatus> statuses) {
        return queryFactory
            .selectFrom(application)
            .from(application)
            .join(application.applicant, applicant).fetchJoin()
            .join(application.applicationForm, applicationForm).fetchJoin()
            .join(applicationForm.team, team).fetchJoin()
            .join(team.generation, generation).fetchJoin()
            .join(application.applicationResult, applicationResult).fetchJoin()
            .join(application.confirmation, confirmation).fetchJoin()
            .where(applicant.applicantId.eq(applicantId)
                .and(application.status.in(statuses)))
            .fetch();
    }

    @Override
    public List<Application> findByApplicantAndApplicationForm(Applicant _applicant, ApplicationForm _applicationForm) {
        return queryFactory
            .selectFrom(application)
            .join(application.applicant, applicant).fetchJoin()
            .join(application.applicationForm, applicationForm).fetchJoin()
            .join(applicationForm.team, team).fetchJoin()
            .join(team.generation, generation).fetchJoin()
            .join(application.applicationResult, applicationResult).fetchJoin()
            .join(application.confirmation, confirmation).fetchJoin()
            .where(applicant.eq(_applicant)
                .and(applicationForm.eq(_applicationForm)))
            .fetch();
    }

    @Override
    public Optional<Application> findByApplicationAndApplicant(Long applicationId, Long applicantId) {
        Application fetchOne
            = queryFactory
            .selectFrom(application)
            .from(application)
            .join(application.applicant, applicant).fetchJoin()
            .join(application.applicationForm, applicationForm).fetchJoin()
            .join(applicationForm.team, team).fetchJoin()
            .join(team.generation, generation).fetchJoin()
            .join(application.applicationResult, applicationResult).fetchJoin()
            .join(application.confirmation, confirmation).fetchJoin()
            .where(application.applicationId.eq(applicationId)
                .and(applicant.applicantId.eq(applicantId)))
            .fetchOne();
        return fetchOne != null ? Optional.of(fetchOne) : Optional.empty();
    }

    @Override
    public List<Application> findByApplicationForm(Long applicationFormId) {
        return queryFactory
            .selectFrom(application)
            .join(application.applicant, applicant).fetchJoin()
            .join(application.applicationForm, applicationForm).fetchJoin()
            .join(applicationForm.team, team).fetchJoin()
            .join(team.generation, generation).fetchJoin()
            .join(application.applicationResult, applicationResult).fetchJoin()
            .join(application.confirmation, confirmation).fetchJoin()
            .where(applicationForm.applicationFormId.eq(applicationFormId))
            .fetch();
    }

    @Override
    public List<Application> findByStatusAndCreatedAtBefore(ApplicationStatus status, LocalDateTime eventOccurredAt) {
        return queryFactory
            .selectFrom(application)
            .join(application.applicant, applicant).fetchJoin()
            .join(application.applicationForm, applicationForm).fetchJoin()
            .join(applicationForm.team, team).fetchJoin()
            .join(team.generation, generation).fetchJoin()
            .join(application.applicationResult, applicationResult).fetchJoin()
            .join(application.confirmation, confirmation).fetchJoin()
            .where(application.status.eq(status)
                .and(application.createdAt.before(eventOccurredAt)))
            .fetch();
    }

    @Override
    public List<Application> findApplicationsByApplicantIn(Generation _generation, List<Applicant> applicants) {
        return queryFactory
            .selectFrom(application)
            .join(application.applicant, applicant).fetchJoin()
            .join(application.applicationForm, applicationForm).fetchJoin()
            .join(applicationForm.team, team).fetchJoin()
            .join(team.generation, generation).fetchJoin()
            .join(application.applicationResult, applicationResult).fetchJoin()
            .join(application.confirmation, confirmation).fetchJoin()
            .where(applicant.in(applicants).and(generation.eq(_generation)))
            .fetch();
    }

}
/**
 * Applicant 연관관계
 * many to one: applicant, applicationForm
 * one to one : applicationResult, confirmation
 */

