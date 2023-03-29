package kr.mashup.branding.repository.member;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.mashup.branding.domain.member.MemberGeneration;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static kr.mashup.branding.domain.generation.QGeneration.generation;
import static kr.mashup.branding.domain.member.QMemberGeneration.memberGeneration;

@RequiredArgsConstructor
public class MemberGenerationRepositoryCustomImpl implements MemberGenerationRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<MemberGeneration> findByMemberIdAndGenerationNumber(Long memberId, Integer generationNumber) {
        return Optional.ofNullable(queryFactory
            .selectFrom(memberGeneration)
            .join(memberGeneration.generation, generation)
            .where(memberGeneration.member.id.eq(memberId), generation.number.eq(generationNumber))
            .fetchOne()
        );
    }
}

