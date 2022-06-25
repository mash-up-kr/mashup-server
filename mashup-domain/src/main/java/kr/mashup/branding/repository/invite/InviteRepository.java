package kr.mashup.branding.repository.invite;

import kr.mashup.branding.domain.invite.Invite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InviteRepository extends JpaRepository<Invite, Long> {

}
