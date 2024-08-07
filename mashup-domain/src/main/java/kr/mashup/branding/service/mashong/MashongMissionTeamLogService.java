package kr.mashup.branding.service.mashong;

import kr.mashup.branding.domain.mashong.*;
import kr.mashup.branding.domain.member.Platform;
import kr.mashup.branding.repository.mashong.MashongMissionTeamLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MashongMissionTeamLogService {
    private final MashongMissionTeamLogRepository mashongMissionTeamLogRepository;

    public Optional<MashongMissionTeamLog> getLastAchievedMissionLog(MashongMission mashongMission, Platform platform, Long generationId) {
        if (mashongMission.getMissionRepeatType() == MissionRepeatType.DAILY) {
            String baseDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            List<MashongMissionTeamLog> mashongMissionLogList = mashongMissionTeamLogRepository.findAllByPlatformAndGenerationIdAndMissionIdAndBaseDate(platform, generationId, mashongMission.getId(), baseDate);
            return mashongMissionLogList.stream().max(Comparator.comparing(MashongMissionTeamLog::getLevel));
        } else {
            List<MashongMissionTeamLog> mashongMissionLogList = mashongMissionTeamLogRepository.findAllByPlatformAndGenerationIdAndMissionId(platform, generationId, mashongMission.getId());
            return mashongMissionLogList.stream().max(Comparator.comparing(MashongMissionTeamLog::getLevel));
        }
    }

    public MashongMissionTeamLog getMissionLog(MashongMissionLevel mashongMissionLevel, Platform platform, Long generationId) {
        if (mashongMissionLevel.getMashongMission().getMissionRepeatType() == MissionRepeatType.DAILY) {
            String baseDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            Optional<MashongMissionTeamLog> mashongMissionLog = mashongMissionTeamLogRepository.findByMissionLevelIdAndPlatformAndGenerationIdAndBaseDate(mashongMissionLevel.getId(), platform, generationId, baseDate);
            return mashongMissionLog.orElseGet(() -> mashongMissionTeamLogRepository.save(MashongMissionTeamLog.of(platform, generationId, mashongMissionLevel, baseDate)));
        } else {
            Optional<MashongMissionTeamLog> mashongMissionLog = mashongMissionTeamLogRepository.findByMissionLevelIdAndPlatformAndGenerationId(mashongMissionLevel.getId(), platform, generationId);
            return mashongMissionLog.orElseGet(() -> mashongMissionTeamLogRepository.save(MashongMissionTeamLog.of(platform, generationId, mashongMissionLevel)));
        }
    }
}
