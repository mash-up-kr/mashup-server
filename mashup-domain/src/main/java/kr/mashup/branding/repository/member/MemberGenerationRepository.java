package kr.mashup.branding.repository.member;

import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.member.MemberGeneration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberGenerationRepository extends JpaRepository<MemberGeneration, Long> {

    void deleteByMember(Member member);
    Optional<MemberGeneration> findByMemberAndGeneration(Member member, Generation generation);
    List<MemberGeneration> findByMember(Member member);

}
