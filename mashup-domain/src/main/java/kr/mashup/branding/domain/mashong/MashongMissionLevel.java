package kr.mashup.branding.domain.mashong;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MashongMissionLevel {
    @Id
    @GeneratedValue
    private Long id;

    private Long level;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "mission_id")
    private MashongMission mashongMission;

    private Long missionGoalValue;

    private Long compensationValue;

    private String title;
}
