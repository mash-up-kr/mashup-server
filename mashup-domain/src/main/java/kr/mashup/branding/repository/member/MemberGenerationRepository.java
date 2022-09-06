package kr.mashup.branding.repository.member;

import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.member.MemberGeneration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberGenerationRepository extends JpaRepository<MemberGeneration, Long> {

    void deleteByMember(Member member);
}
