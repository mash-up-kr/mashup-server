package kr.mashup.branding.domain.mashong;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Comparator;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MashongMission {
    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private MissionType missionType;

    @Enumerated(EnumType.STRING)
    private MissionRepeatType missionRepeatType;

    @Enumerated(EnumType.STRING)
    private MissionStrategyType missionStrategyType;

    private String name;

    @OneToMany(mappedBy = "mashongMission")
    private List<MashongMissionLevel> mashongMissionLevelList;

    public MashongMissionLevel getFirstMissionLevel() {
        return this.mashongMissionLevelList.stream()
                .min(Comparator.comparing(MashongMissionLevel::getLevel))
                .orElseThrow(IllegalStateException::new);
    }

    public MashongMissionLevel getNextMissionLevel(Long level) {
        return mashongMissionLevelList.stream()
            .filter(missionLevel -> missionLevel.getLevel() == level + 1)
            .findFirst()
            .orElseGet(null);
    }
}
