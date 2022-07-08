package kr.mashup.branding.service.scorehistory;

import kr.mashup.branding.domain.member.Member;
import kr.mashup.branding.domain.scorehistory.ScoreHistory;
import kr.mashup.branding.repository.scorehistory.ScoreHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ScoreHistoryService {

    private final ScoreHistoryRepository scoreHistoryRepository;

    @Transactional
    public ScoreHistory save(ScoreHistory scoreHistory){

        scoreHistoryRepository.save(scoreHistory);

        return scoreHistory;
    }

    public List<ScoreHistory> getByMember(Member member){
        return scoreHistoryRepository.findByMember(member);
    }

    @Transactional
    public void deleteById(Long id){
        scoreHistoryRepository.deleteById(id);
    }

}
