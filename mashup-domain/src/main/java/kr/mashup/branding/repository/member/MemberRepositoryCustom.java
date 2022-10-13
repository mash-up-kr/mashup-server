package kr.mashup.branding.repository.member;

import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.member.Platform;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static kr.mashup.branding.repository.member.MemberRepositoryCustomImpl.MemberScoreQueryResult;

public interface MemberRepositoryCustom {

    Page<MemberScoreQueryResult> findAllActiveByGeneration(Generation generation, Platform platform, String searchName, Pageable pageable);
}
/**
 * Member 연관관계
 * one to many : memberGeneration, attendance
 */
