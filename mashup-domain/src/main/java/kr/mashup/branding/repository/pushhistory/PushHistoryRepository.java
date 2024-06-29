package kr.mashup.branding.repository.pushhistory;

import kr.mashup.branding.domain.pushhistory.PushHistory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PushHistoryRepository extends JpaRepository<PushHistory, Long> {
    List<PushHistory> findAllByMemberId(Long memberId, Pageable pageable);
}
