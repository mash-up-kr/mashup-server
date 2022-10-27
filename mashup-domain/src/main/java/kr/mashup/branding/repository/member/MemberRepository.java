package kr.mashup.branding.repository.member;

import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.member.MemberStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

    Optional<Member> findByIdentification(String identification);

    Boolean existsByIdentification(String identification);

    Page<Member> findAllByStatus(MemberStatus status, Pageable pageable);
}
/**
 * Member 연관관계
 * one to many : memberGeneration, attendance
 */
