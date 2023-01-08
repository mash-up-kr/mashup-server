package kr.mashup.branding.repository.emailnotification;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.mashup.branding.domain.email.EmailNotification;
import kr.mashup.branding.util.QueryUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static kr.mashup.branding.domain.adminmember.entity.QAdminMember.adminMember;
import static kr.mashup.branding.domain.email.QEmailNotification.emailNotification;
import static kr.mashup.branding.domain.generation.QGeneration.generation;

@RequiredArgsConstructor
public class EmailNotificationRepositoryCustomImpl implements EmailNotificationRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<EmailNotification> findBySearchWord(Optional<String> searchWord, Pageable pageable) {
        QueryResults<EmailNotification> results = queryFactory
            .selectFrom(emailNotification)
            .innerJoin(emailNotification.sender, adminMember).fetchJoin()
            .innerJoin(emailNotification.generation, generation).fetchJoin()
            .where(isContainSearchWord(searchWord))
            .fetchResults();

        return QueryUtils.toPage(results, pageable);
    }

    private BooleanExpression isContainSearchWord(Optional<String> searchWord) {
        if(searchWord.isEmpty()){
            return null;
        }

        final String searchWordParam = searchWord.get();
        return emailNotification.memo.contains(searchWordParam);
    }
}
