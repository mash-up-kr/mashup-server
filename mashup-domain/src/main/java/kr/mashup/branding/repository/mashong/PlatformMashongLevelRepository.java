package kr.mashup.branding.repository.mashong;

import kr.mashup.branding.domain.mashong.PlatformMashongLevel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlatformMashongLevelRepository extends JpaRepository<PlatformMashongLevel, Long> {

    Optional<PlatformMashongLevel> findByLevel(int level);
}
