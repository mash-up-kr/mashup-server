package kr.mashup.branding.repository.emailnotification;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.mashup.branding.domain.adminmember.entity.QAdminMember;
import kr.mashup.branding.domain.application.QApplication;
import kr.mashup.branding.domain.email.EmailRequest;
import kr.mashup.branding.domain.email.QEmailNotification;
import kr.mashup.branding.domain.email.QEmailRequest;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static kr.mashup.branding.domain.adminmember.entity.QAdminMember.adminMember;
import static kr.mashup.branding.domain.application.QApplication.application;
import static kr.mashup.branding.domain.email.QEmailNotification.emailNotification;
import static kr.mashup.branding.domain.email.QEmailRequest.emailRequest;

@RequiredArgsConstructor
public class EmailRequestRepositoryCustomImpl implements EmailRequestRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<EmailRequest> findByApplicationId(Long applicationId) {

        return queryFactory
            .selectFrom(emailRequest)
            .innerJoin(emailRequest.application, application).fetchJoin()
            .innerJoin(emailRequest.emailNotification, emailNotification).fetchJoin()
            .innerJoin(emailNotification.sender, adminMember).fetchJoin()
            .where(application.applicationId.eq(applicationId))
            .fetch();
    }
}
