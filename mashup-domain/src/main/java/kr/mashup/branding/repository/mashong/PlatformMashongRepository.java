package kr.mashup.branding.repository.mashong;

import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.mashong.PlatformMashong;
import kr.mashup.branding.domain.mashong.PlatformMashongLevel;
import kr.mashup.branding.domain.member.Platform;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlatformMashongRepository extends JpaRepository<PlatformMashong, Long> {

    Optional<PlatformMashong> findByPlatformAndGeneration(Platform platform, Generation generation);
}
