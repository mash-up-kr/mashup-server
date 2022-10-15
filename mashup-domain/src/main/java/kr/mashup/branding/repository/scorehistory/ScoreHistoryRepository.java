package kr.mashup.branding.repository.scorehistory;

import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.scorehistory.ScoreHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScoreHistoryRepository extends JpaRepository<ScoreHistory, Long> {

    List<ScoreHistory> findByMember(Member member);

    List<ScoreHistory> findByMemberAndGenerationOrderByDateAsc(Member member, Generation generation);
}
/**
 * ScoreHistory 연관관계
 * many to one : generation, member
 */