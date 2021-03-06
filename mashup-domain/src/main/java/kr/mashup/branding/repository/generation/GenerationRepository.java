package kr.mashup.branding.repository.generation;

import kr.mashup.branding.domain.generation.Generation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GenerationRepository extends JpaRepository<Generation, Long> {

	Optional<Generation> findByNumber(Integer number);
}
