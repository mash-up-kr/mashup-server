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
public class MashongPopcorn {
    @Id
    @GeneratedValue
    private Long id;

    private Long memberGenerationId;

    private Long popcorn;

    public static MashongPopcorn of(
        Long memberGenerationId
    ) {
        return new MashongPopcorn(memberGenerationId, 0L);
    }

    private MashongPopcorn(
        Long memberGenerationId,
        Long popcorn
    ) {
        this.memberGenerationId = memberGenerationId;
        this.popcorn = popcorn;
    }
}
