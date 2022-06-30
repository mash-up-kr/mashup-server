package kr.mashup.branding.repository.invite;

import kr.mashup.branding.domain.invite.Invite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InviteRepository extends JpaRepository<Invite, Long> {
    Optional<Invite> findByCode(String inviteCode);
}
