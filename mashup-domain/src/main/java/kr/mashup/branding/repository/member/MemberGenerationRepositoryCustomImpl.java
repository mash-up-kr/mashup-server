package kr.mashup.branding.repository.member;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.mashup.branding.domain.member.MemberGeneration;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static kr.mashup.branding.domain.generation.QGeneration.generation;
import static kr.mashup.branding.domain.member.QMember.member;
import static kr.mashup.branding.domain.member.QMemberGeneration.memberGeneration;

@RequiredArgsConstructor
public class MemberGenerationRepositoryCustomImpl implements MemberGenerationRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<MemberGeneration> findByMemberIdAndGenerationNumber(Long memberId, Integer generationNumber) {
        return Optional.ofNullable(queryFactory
            .selectFrom(memberGeneration)
            .join(memberGeneration.generation, generation)
            .join(memberGeneration.member, member)
            .where(member.id.eq(memberId), generation.number.eq(generationNumber))
            .fetchOne()
        );
    }

    @Override
    public List<MemberGeneration> findByMemberIdsAndGenerationNumber(List<Long> memberIds, Integer generationNumber) {
        return queryFactory
            .selectFrom(memberGeneration)
            .join(memberGeneration.member, member).fetchJoin()
            .join(memberGeneration.generation, generation)
            .where(member.id.in(memberIds), generation.number.eq(generationNumber))
            .fetch();
    }
}

