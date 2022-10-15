package kr.mashup.branding.repository.notification;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.mashup.branding.domain.notification.Notification;
import kr.mashup.branding.util.QueryUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static kr.mashup.branding.domain.notification.QNotification.notification;

@RequiredArgsConstructor
public class NotificationRepositoryCustomImpl implements NotificationRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Notification> findWithSearchWord(String searchWord, Pageable pageable) {
        QueryResults<Notification> fetchResults = queryFactory
            .selectFrom(notification)
            .join(notification.sender).fetchJoin()
            .where(notification.name.contains(searchWord) // 발송 메모
                .or(notification.sender.phoneNumber.contains(searchWord)) // 발송 번호
                .or(notification.sender.position.stringValue().containsIgnoreCase(searchWord)))
            .fetchResults();
        return QueryUtils.toPage(fetchResults, pageable);
    }
}
