package kr.mashup.branding.repository.member;

import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.member.Platform;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.MonthDay;
import java.util.List;

import static kr.mashup.branding.repository.member.MemberRepositoryCustomImpl.MemberScoreQueryResult;

public interface MemberRepositoryCustom {

    Page<MemberScoreQueryResult> findAllByGeneration(Generation generation, Platform platform, String searchName, Pageable pageable);

    List<Member> findActiveByPlatformAndGeneration(Platform platform, Generation generation);

    Page<Member> findActiveByPlatformAndGeneration(Platform platform, Generation generation, Pageable pageable);

    List<Member> findAllByCurrentGenerationAt(LocalDate at);

    List<Member> findAllActiveByGeneration(Generation generation);

    List<Member> retrieveByBirthDate(Generation generation, MonthDay monthDay);
}
