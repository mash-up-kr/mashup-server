package kr.mashup.branding.repository.member;

import java.util.List;
import java.util.Optional;

import kr.mashup.branding.domain.generation.Generation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.member.Platform;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @EntityGraph(attributePaths = {"generation"}, type = EntityGraph.EntityGraphType.LOAD)
    Optional<Member> findByIdentification(String identification);

    Long countByPlatformAndGeneration(Platform platform, Generation generation);

    List<Member> findAllByPlatformAndGeneration(Platform platform, Generation generation);

    Boolean existsByIdentification(String identification);

    Page<Member> findBy(Pageable pageable);
}
