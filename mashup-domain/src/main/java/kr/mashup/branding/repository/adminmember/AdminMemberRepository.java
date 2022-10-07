package kr.mashup.branding.repository.adminmember;

import java.util.Optional;

import kr.mashup.branding.domain.adminmember.entity.AdminMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminMemberRepository extends JpaRepository<AdminMember, Long> {
    Optional<AdminMember> findByUsername(String username);

    boolean existsByUsername(String username);
}
