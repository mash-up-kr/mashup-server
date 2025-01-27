package kr.mashup.branding.repository.member;

import kr.mashup.branding.domain.member.MemberGeneration;

import java.util.List;
import java.util.Optional;

public interface MemberGenerationRepositoryCustom {
    Optional<MemberGeneration> findByMemberIdAndGenerationNumber(Long memberId, Integer generationNumber);

    List<MemberGeneration> findByMemberIdsAndGenerationNumber(List<Long> memberIds, Integer generationNumber);

    Optional<MemberGeneration> findLatestByMemberId(Long memberId);
}
