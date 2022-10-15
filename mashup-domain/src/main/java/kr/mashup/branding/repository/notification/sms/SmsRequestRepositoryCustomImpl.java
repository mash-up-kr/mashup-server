package kr.mashup.branding.repository.notification.sms;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.mashup.branding.domain.notification.sms.SmsRequest;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static kr.mashup.branding.domain.notification.sms.QSmsRequest.smsRequest;

@RequiredArgsConstructor
public class SmsRequestRepositoryCustomImpl implements SmsRequestRepositoryCustom{
    private final JPAQueryFactory queryFactory;


    @Override
    public List<SmsRequest> findByRecipient(Long applicantId) {
        return queryFactory
            .selectFrom(smsRequest)
            .join(smsRequest.notification).fetchJoin()
            .join(smsRequest.recipientApplicant).fetchJoin()
            .where(smsRequest.recipientApplicant.applicantId.eq(applicantId))
            .fetch();
    }
}
