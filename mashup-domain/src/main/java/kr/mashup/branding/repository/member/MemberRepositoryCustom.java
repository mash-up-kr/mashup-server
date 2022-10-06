package kr.mashup.branding.repository.member;

import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.member.Platform;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import static kr.mashup.branding.repository.member.MemberRepositoryCustomImpl.*;

public interface MemberRepositoryCustom {

    Page<MemberScoreQueryResult> findAllActiveByGeneration(Generation generation, Platform platform, String searchName, Pageable pageable);
}
