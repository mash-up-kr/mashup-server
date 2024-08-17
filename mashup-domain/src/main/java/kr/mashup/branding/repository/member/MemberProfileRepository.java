package kr.mashup.branding.repository.member;

import kr.mashup.branding.domain.member.MemberProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberProfileRepository extends JpaRepository<MemberProfile, Long>, MemberProfileRepositoryCustom {

    Optional<MemberProfile> findByMemberId(Long memberId);
}
