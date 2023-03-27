package kr.mashup.branding.service.danggn;

import kr.mashup.branding.domain.danggn.DanggnScore;
import kr.mashup.branding.domain.member.MemberGeneration;
import kr.mashup.branding.repository.danggn.DanggnScoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DanggnScoreService {
    private final DanggnScoreRepository danggnScoreRepository;

    public DanggnScore findByMemberGeneration(MemberGeneration memberGeneration) {
        return danggnScoreRepository.findByMemberGeneration(memberGeneration)
            .orElseGet(() -> danggnScoreRepository.save(DanggnScore.of(memberGeneration, 0L)));
    }
}
