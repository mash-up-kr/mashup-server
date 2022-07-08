package kr.mashup.branding.repository.scorehistory;

import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.scorehistory.ScoreHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScoreHistoryRepository extends JpaRepository<ScoreHistory, Long> {

    // TODO: N+1 고려하면서 쿼리 짜기
    List<ScoreHistory> findByMember(Member member);
}
