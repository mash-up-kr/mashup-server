package kr.mashup.branding.repository.member;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.mashup.branding.domain.member.MemberGeneration;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class MemberGenerationRepositoryCustomImpl implements MemberGenerationRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<MemberGeneration> findByMemberIdAndGenerationNumber(Long memberId, Integer generationNumber) {
        // ToDo implement below
        return Optional.empty();
    }
}

