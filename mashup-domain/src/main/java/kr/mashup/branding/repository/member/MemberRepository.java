package kr.mashup.branding.repository.member;

import kr.mashup.branding.domain.member.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @EntityGraph(attributePaths = {"generation"}, type = EntityGraph.EntityGraphType.LOAD)
    Optional<Member> findByIdentification(String identification);
}
