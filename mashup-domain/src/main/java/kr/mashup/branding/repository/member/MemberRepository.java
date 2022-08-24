package kr.mashup.branding.repository.member;

import java.util.List;
import java.util.Optional;

import kr.mashup.branding.domain.generation.Generation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.member.Platform;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByIdentification(String identification);

    @Query("select count(m) from Member m join m.memberGenerations mg where mg.generation = :generation and m.platform = :platform")
    Long countByPlatformAndGeneration(@Param("platform") Platform platform, @Param("generation") Generation generation);

    @Query("select m from Member m join m.memberGenerations mg where mg.generation = :generation and m.platform = :platform")
    List<Member> findAllByPlatformAndGeneration(@Param("platform") Platform platform, @Param("generation") Generation generation);

    Boolean existsByIdentification(String identification);

    Page<Member> findBy(Pageable pageable);
}
