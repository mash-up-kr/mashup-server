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
public class MashongMissionLevel {
    @Id
    @GeneratedValue
    private Long id;

    private Long level;

    private Long missionId;

    private Long missionGoalValue;

    private Long compensationValue;

    private String title;
}
