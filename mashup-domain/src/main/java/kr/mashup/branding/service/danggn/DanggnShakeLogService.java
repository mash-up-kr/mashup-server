package kr.mashup.branding.service.danggn;

import kr.mashup.branding.domain.danggn.DanggnShakeLog;
import kr.mashup.branding.domain.member.MemberGeneration;
import kr.mashup.branding.repository.danggn.DanggnShakeLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class DanggnShakeLogService {
    private final DanggnShakeLogRepository danggnShakeLogRepository;

    @Transactional
    public DanggnShakeLog createLog(MemberGeneration memberGeneration, Long score) {
        return danggnShakeLogRepository.save(
            DanggnShakeLog.of(memberGeneration, score)
        );
    }
}
