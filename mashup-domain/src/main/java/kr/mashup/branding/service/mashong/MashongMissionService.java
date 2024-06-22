package kr.mashup.branding.service.mashong;

import kr.mashup.branding.domain.mashong.Exception.MashongMissionNotFoundException;
import kr.mashup.branding.domain.mashong.MashongMission;
import kr.mashup.branding.domain.mashong.MissionStrategyType;
import kr.mashup.branding.repository.mashong.MashongMissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MashongMissionService {
    private final MashongMissionRepository mashongMissionRepository;

    public List<MashongMission> findAll() {
        return mashongMissionRepository.findAll();
    }

    public MashongMission findMissionByStrategyType(MissionStrategyType missionStrategyType) {
        return mashongMissionRepository.findByMissionStrategyType(missionStrategyType).orElseThrow(MashongMissionNotFoundException::new);
    }
}
