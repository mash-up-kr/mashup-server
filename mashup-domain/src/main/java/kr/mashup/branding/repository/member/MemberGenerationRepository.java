package kr.mashup.branding.repository.member;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.member.MemberGeneration;

public interface MemberGenerationRepository extends JpaRepository<MemberGeneration, Long>, MemberGenerationRepositoryCustom {

    void deleteByMember(Member member);
    Optional<MemberGeneration> findByMemberAndGeneration(Member member, Generation generation);
    List<MemberGeneration> findByMember(Member member);
    List<MemberGeneration> findByGeneration(Generation generation);

}
/**
 * MemberGeneration 연관관계
 * many to one: member, generation
 */
