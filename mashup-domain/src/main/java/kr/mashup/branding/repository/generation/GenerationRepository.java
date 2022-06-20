package kr.mashup.branding.repository.generation;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.mashup.branding.domain.generation.Generation;

public interface GenerationRepository extends JpaRepository<Generation, Long> {
}
