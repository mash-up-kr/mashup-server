package kr.mashup.branding.domain.adminmember;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminMemberRepository extends JpaRepository<AdminMember, Long> {
    Optional<AdminMember> findByUsername(String username);

    boolean existsByUsername(String username);
}
