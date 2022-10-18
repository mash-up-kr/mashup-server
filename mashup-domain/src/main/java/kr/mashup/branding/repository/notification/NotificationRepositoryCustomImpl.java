package kr.mashup.branding.repository.notification;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.mashup.branding.domain.adminmember.entity.QAdminMember;
import kr.mashup.branding.domain.notification.Notification;
import kr.mashup.branding.util.QueryUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static kr.mashup.branding.domain.adminmember.entity.QAdminMember.adminMember;
import static kr.mashup.branding.domain.notification.QNotification.notification;

@RequiredArgsConstructor
public class NotificationRepositoryCustomImpl implements NotificationRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Notification> findWithSearchWord(String searchWord, Pageable pageable) {
        QueryResults<Notification> fetchResults = queryFactory
            .selectFrom(notification)
            .join(notification.sender, adminMember).fetchJoin()
            .where(notification.name.contains(searchWord) // 발송 메모
                .or(adminMember.phoneNumber.contains(searchWord)) // 발송 번호
                .or(adminMember.position.stringValue().containsIgnoreCase(searchWord)))
            .fetchResults();
        return QueryUtils.toPage(fetchResults, pageable);
    }
}
