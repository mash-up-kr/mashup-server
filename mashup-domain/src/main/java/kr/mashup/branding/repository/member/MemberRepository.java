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

    Boolean existsByIdentification(String identification);

    Page<Member> findAllByStatus(MemberStatus status, Pageable pageable);

}
/**
 * Member 연관관계
 * one to many : memberGeneration, attendance
 */
