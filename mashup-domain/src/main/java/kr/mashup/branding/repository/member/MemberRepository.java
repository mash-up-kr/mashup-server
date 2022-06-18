package kr.mashup.branding.repository.member;

import kr.mashup.branding.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
