package kr.mashup.branding.repository.mashong;

import kr.mashup.branding.domain.mashong.PlatformMashongLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PlatformMashongLevelRepository extends JpaRepository<PlatformMashongLevel, Long> {

    @Query("SELECT ml FROM PlatformMashongLevel AS ml WHERE ml.level = :level + 1")
    Optional<PlatformMashongLevel> findNextLevelByLevel(int level);

    @Query("SELECT ml FROM PlatformMashongLevel AS ml WHERE ml.level = (SELECT MAX(level) FROM PlatformMashongLevel)")
    PlatformMashongLevel findMaxLevel();
}
