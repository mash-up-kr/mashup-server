package kr.mashup.branding.service.mashong;

import kr.mashup.branding.domain.mashong.MashongMissionLevel;
import kr.mashup.branding.repository.mashong.MashongMissionLevelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MashongMissionLevelService {
    private final MashongMissionLevelRepository mashongMissionLevelRepository;

    public MashongMissionLevel getFirstMissionLevel(Long missionId) {
        List<MashongMissionLevel> mashongMissionLevelList = mashongMissionLevelRepository.findAllByMissionId(missionId);
        return mashongMissionLevelList.stream().max(Comparator.comparing(MashongMissionLevel::getLevel)).orElseThrow(RuntimeException::new); //todo runtime
    }

    public MashongMissionLevel findMissionLevel(Long missionLevelId) {
        return mashongMissionLevelRepository.findById(missionLevelId).orElseThrow(RuntimeException::new); //todo runtime;
    }
}
