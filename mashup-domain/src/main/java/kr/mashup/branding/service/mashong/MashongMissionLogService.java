package kr.mashup.branding.service.mashong;

import kr.mashup.branding.domain.mashong.*;
import kr.mashup.branding.repository.mashong.MashongMissionLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MashongMissionLogService {
    private final MashongMissionLogRepository mashongMissionLogRepository;

    public Optional<MashongMissionLog> getLastAchievedMissionLog(MashongMission mashongMission, Long memberGenerationId) {
        if (mashongMission.getMissionRepeatType() == MissionRepeatType.DAILY) {
            String baseDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            List<MashongMissionLog> mashongMissionLogList = mashongMissionLogRepository.findAllByMemberGenerationIdAndMissionIdAndBaseDate(memberGenerationId, mashongMission.getId(), baseDate);
            return mashongMissionLogList.stream().max(Comparator.comparing(MashongMissionLog::getLevel));
        } else {
            List<MashongMissionLog> mashongMissionLogList = mashongMissionLogRepository.findAllByMemberGenerationIdAndMissionId(memberGenerationId, mashongMission.getId());
            return mashongMissionLogList.stream().max(Comparator.comparing(MashongMissionLog::getLevel));
        }
    }

    public MashongMissionLog getMissionLog(MashongMissionLevel mashongMissionLevel, Long memberGenerationId) {
        if (mashongMissionLevel.getMashongMission().getMissionRepeatType() == MissionRepeatType.DAILY) {
            String baseDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            Optional<MashongMissionLog> mashongMissionLog = mashongMissionLogRepository.findByMissionLevelIdAndMemberGenerationIdAndBaseDate(mashongMissionLevel.getId(), memberGenerationId, baseDate);
            return mashongMissionLog.orElseGet(() -> mashongMissionLogRepository.save(MashongMissionLog.of(memberGenerationId, mashongMissionLevel, baseDate)));
        } else {
            Optional<MashongMissionLog> mashongMissionLog = mashongMissionLogRepository.findByMissionLevelIdAndMemberGenerationId(mashongMissionLevel.getId(), memberGenerationId);
            return mashongMissionLog.orElseGet(() -> mashongMissionLogRepository.save(MashongMissionLog.of(memberGenerationId, mashongMissionLevel)));
        }
    }
}
