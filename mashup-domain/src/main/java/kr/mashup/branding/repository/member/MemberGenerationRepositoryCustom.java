package kr.mashup.branding.repository.member;

import kr.mashup.branding.domain.member.MemberGeneration;

import java.util.Optional;

public interface MemberGenerationRepositoryCustom {
    Optional<MemberGeneration> findByMemberIdAndGenerationNumber(Long memberId, Integer generationNumber);
}