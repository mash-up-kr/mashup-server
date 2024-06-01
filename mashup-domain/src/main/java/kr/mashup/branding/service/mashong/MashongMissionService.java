package kr.mashup.branding.service.mashong;

import kr.mashup.branding.domain.mashong.MashongMission;
import kr.mashup.branding.repository.mashong.MashongMissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MashongMissionService {
    private final MashongMissionRepository mashongMissionRepository;

    public MashongMission findMission(Long missionId) {
        return mashongMissionRepository.findById(missionId).orElseThrow(RuntimeException::new);//todo runtimeexception
    }
}
