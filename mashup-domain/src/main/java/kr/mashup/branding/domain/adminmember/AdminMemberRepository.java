package kr.mashup.branding.domain.adminmember;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminMemberRepository extends JpaRepository<AdminMember, Long> {
    Optional<AdminMember> findByProviderUserId(String providerUserId);
}
