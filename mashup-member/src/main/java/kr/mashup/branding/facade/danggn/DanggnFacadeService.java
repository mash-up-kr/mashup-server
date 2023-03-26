package kr.mashup.branding.facade.danggn;

import kr.mashup.branding.domain.danggn.DanggnScore;
import kr.mashup.branding.domain.member.MemberGeneration;
import kr.mashup.branding.service.danggn.DanggnShakeLogService;
import kr.mashup.branding.service.member.MemberService;
import kr.mashup.branding.ui.danggn.response.DanggnScoreResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class DanggnFacadeService {
    private final MemberService memberService;

    private final DanggnShakeLogService danggnShakeLogService;


    @Transactional
    public DanggnScoreResponse addScore(
        Long memberId,
        Integer generationNumber,
        Long score
    ) {
        final MemberGeneration memberGeneration = memberService.findByMemberIdAndGenerationNumber(memberId, generationNumber);
        final DanggnScore danggnScore = memberGeneration.getDanggnScore();
        danggnScore.addScore(score);
        danggnShakeLogService.createLog(memberGeneration, score);
        return DanggnScoreResponse.of(memberGeneration);
    }
}
