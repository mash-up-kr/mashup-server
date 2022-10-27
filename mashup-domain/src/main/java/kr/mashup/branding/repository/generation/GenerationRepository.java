package kr.mashup.branding.repository.generation;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.mashup.branding.domain.generation.Generation;

public interface GenerationRepository extends JpaRepository<Generation, Long> {

	Optional<Generation> findByNumber(Integer number);

	Generation findTop1ByOrderByNumberDesc();
}
/**
 * Generation 연관관계
 * one to many : schedule, memberGeneration, invites
 */