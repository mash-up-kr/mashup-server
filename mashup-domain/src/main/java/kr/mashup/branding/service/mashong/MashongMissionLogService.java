package kr.mashup.branding.service.mashong;

import kr.mashup.branding.domain.mashong.MashongMissionLevel;
import kr.mashup.branding.domain.mashong.MashongMissionLog;
import kr.mashup.branding.repository.mashong.MashongMissionLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MashongMissionLogService {
    private final MashongMissionLogRepository mashongMissionLogRepository;

    public Optional<MashongMissionLog> getLastAchievedMissionLog(Long missionId, Long memberGenerationId) {
        List<MashongMissionLog> mashongMissionLogList = mashongMissionLogRepository.findAllByMemberGenerationIdAndMissionId(memberGenerationId, missionId);
        return mashongMissionLogList.stream().max(Comparator.comparing(MashongMissionLog::getLevel));
    }

    public void save(Long memberGenerationId, MashongMissionLevel mashongMissionLevel) {
        mashongMissionLogRepository.save(MashongMissionLog.of(memberGenerationId, mashongMissionLevel, LocalDateTime.now()));
    }
}
