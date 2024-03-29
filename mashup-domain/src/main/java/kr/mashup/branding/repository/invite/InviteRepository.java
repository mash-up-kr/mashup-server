package kr.mashup.branding.repository.invite;

import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.invite.Invite;
import kr.mashup.branding.domain.member.Platform;
import org.hibernate.validator.resourceloading.PlatformResourceBundleLocator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InviteRepository extends JpaRepository<Invite, Long> {

    Optional<Invite> findByCode(String inviteCode);

    Optional<Invite> findByPlatformAndGeneration(Platform platform, Generation generation);

    List<Invite> findAllByGeneration(Generation generation);
}
/**
 * Invite 연관관계
 * many to one : generation
 */