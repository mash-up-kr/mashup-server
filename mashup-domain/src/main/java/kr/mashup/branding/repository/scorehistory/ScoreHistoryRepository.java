package kr.mashup.branding.repository.scorehistory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.scorehistory.ScoreHistory;

public interface ScoreHistoryRepository extends JpaRepository<ScoreHistory, Long>, ScoreHistoryRepositoryCustom {

    List<ScoreHistory> findByMember(Member member);

    List<ScoreHistory> findByMemberAndGenerationOrderByDateAsc(Member member, Generation generation);

    void deleteByMember(Member member);
}
/**
 * ScoreHistory 연관관계
 * many to one : generation, member
 */
