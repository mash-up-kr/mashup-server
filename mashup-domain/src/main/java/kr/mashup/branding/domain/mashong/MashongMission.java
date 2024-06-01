package kr.mashup.branding.domain.mashong;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MashongMission {
    @Id
    @GeneratedValue
    private Long id;

    private MissionType missionType;

    private MissionStrategyType missionStrategyType;

    private String name;
}
