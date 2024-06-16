package kr.mashup.branding.domain.mashong;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
    private MissionStrategyType missionStrategyType;

    private String name;
}
