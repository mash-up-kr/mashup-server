package kr.mashup.branding.repository.member;

import java.util.List;
import java.util.Optional;

import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.member.MemberStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.member.Platform;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

    Optional<Member> findByIdentification(String identification);

    @Query("select count(m) from Member m join m.memberGenerations mg where mg.generation = :generation and mg.platform = :platform  and m.status = 'ACTIVE'")
    Long countActiveByPlatformAndGeneration(@Param("platform") Platform platform, @Param("generation") Generation generation);

    @Query("select m from Member m join m.memberGenerations mg where mg.generation = :generation and mg.platform = :platform and m.status = 'ACTIVE'")
    List<Member> findAllActiveByPlatformAndGeneration(@Param("platform") Platform platform, @Param("generation") Generation generation);

    @Query("select m from Member m join m.memberGenerations mg where mg.generation = :generation and mg.platform = :platform  and m.status = 'ACTIVE'")
    Page<Member> findAllActiveByPlatformAndGeneration(@Param("platform") Platform platform, @Param("generation") Generation generation, Pageable pageable);

    Boolean existsByIdentification(String identification);

    Page<Member> findAllByStatus(MemberStatus status, Pageable pageable);


    @Query("select m from Member m join m.memberGenerations mg on mg.generation = :generation join ScoreHistory sh on sh.member = m where m.status = 'ACTIVE' group by m order by sum(sh.score) asc")
    Page<Member> findAllActiveByGenerationOrderByScoreAsc(@Param("generation") Generation generation, Pageable pageable);

    @Query("select m from Member m join m.memberGenerations mg on mg.generation = :generation join ScoreHistory sh on sh.member = m where m.status = 'ACTIVE' and m.name = :name group by m order by sum(sh.score) desc")
    Page<Member> findAllActiveByGenerationOrderByScoreDescWithName(@Param("generation") Generation generation, @Param("name") String name, Pageable pageable);

    @Query("select m from Member m join m.memberGenerations mg on mg.generation = :generation join ScoreHistory sh on sh.member = m where m.status = 'ACTIVE' and m.name = :name group by m order by sum(sh.score) asc")
    Page<Member> findAllActiveByGenerationOrderByScoreAscWithName(@Param("generation") Generation generation,@Param("name") String name, Pageable pageable);

}
