package kr.mashup.branding.service.mashong;

import kr.mashup.branding.domain.mashong.MashongMissionLevel;
import kr.mashup.branding.repository.mashong.MashongMissionLevelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MashongMissionLevelService {
    private final MashongMissionLevelRepository mashongMissionLevelRepository;

    public MashongMissionLevel findMissionLevel(Long missionLevelId) {
        return mashongMissionLevelRepository.findById(missionLevelId).orElseThrow(RuntimeException::new); //todo runtime;
    }
}
