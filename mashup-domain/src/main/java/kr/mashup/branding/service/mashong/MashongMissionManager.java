package kr.mashup.branding.service.mashong;

import kr.mashup.branding.domain.mashong.MissionStrategyType;
import kr.mashup.branding.service.mashong.missions.MissionStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class MashongMissionManager {
    private Map<MissionStrategyType, MissionStrategy> mashongMissionStrategyMap;

    @Autowired
    public MashongMissionManager(List<MissionStrategy> missionStrategies) {
        mashongMissionStrategyMap = missionStrategies.stream()
            .collect(Collectors.toMap(
                    MissionStrategy::getMissionStrategyType,
                    strategy -> strategy,
                    (existing, replacement) -> replacement
                )
            );
    }

    public MissionStrategy getStrategy(MissionStrategyType missionStrategyType) {
        return mashongMissionStrategyMap.get(missionStrategyType);
    }
}
