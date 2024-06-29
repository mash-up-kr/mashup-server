package kr.mashup.branding.repository.mashong;

import kr.mashup.branding.domain.mashong.MashongPopcorn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MashongPopcornRepository extends JpaRepository<MashongPopcorn, Long> {
    @Modifying
    @Query("UPDATE MashongPopcorn x SET x.popcorn = x.popcorn + :value WHERE x.id = :id")
    void increasePopcorn(Long id, Long value);

    Optional<MashongPopcorn> findByMemberGenerationId(Long id);
}
